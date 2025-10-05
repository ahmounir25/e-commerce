package com.newProject.first.service;

import com.newProject.first.DAO.*;
import com.newProject.first.DTO.*;
import com.newProject.first.entity.Cart;
import com.newProject.first.entity.RefreshToken;
import com.newProject.first.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

@Service
public class userServiceImpl implements userService {
    private userRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private PasswordEncoder refreshTokenEncoder;
    private JwtService jwtService;
    private refreshTokenRepo refreshTokenRepo;
    private roleRepository roleRepository;
    private EmailVerificationService verificationService;
    @Value("${jwt.expiryRefreshToken}")
    private long expirationTimeForRefresh;

    @Autowired
    public userServiceImpl(userRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           PasswordEncoder refreshTokenEncoder,
                           JwtService jwtService,
                           refreshTokenRepo refreshTokenRep,
                           roleRepository roleRepository,
                           EmailVerificationService verificationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenEncoder = refreshTokenEncoder;
        this.jwtService = jwtService;
        this.refreshTokenRepo = refreshTokenRep;
        this.roleRepository = roleRepository;
        this.verificationService=verificationService;
    }

    @Override
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }
        String hashedPass = passwordEncoder.encode(request.getPassword());

        User user = new User(request.getFirstName(), request.getLastName(), request.getEmail(), hashedPass);
        user.getRoles().add(roleRepository.findByRole("USER").orElseThrow(() -> new RuntimeException("not found")));
        user.setCart(new Cart(new Date()));
        user.getCart().setUser(user);
        String verificationToken= jwtService.generateVerifyToken(request.getEmail());
        userRepository.save(user);
        verificationService.sendVerifyMail(request.getEmail(),verificationToken);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        User user = userRepository.findUserByEmail(loginRequest.getEmail()).orElseThrow
                ( ()->new RuntimeException("There is no User with this Credentials"));

        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            // generate the Tokens
            String accessToken = jwtService.generateAccessToken(user.getEmail());
            String refreshToken = jwtService.generateRefreshToken(user.getEmail());
            // hashing refresh token before save it to DB
            String hashedRefreshToken = hashToken(refreshToken);

            RefreshToken entityRefresh = new RefreshToken();
            entityRefresh.setEmail(user.getEmail());
            entityRefresh.setToken(hashedRefreshToken);
            entityRefresh.setExpiryDate(new Date(System.currentTimeMillis() + expirationTimeForRefresh));
            // Save To DB
            refreshTokenRepo.save(entityRefresh);
            return new LoginResponse(accessToken, refreshToken);
        } else {
            throw new RuntimeException("Invalid Credentials");
        }

    }

    // TODO: CHANGE ENDPOINT TO TAKE EMPTY BODY //Done
    // we added @Transactional cause there is logic before deletion related to data base (need to be atomic)
    @Override
    @Transactional
    public void logout(String email) {
        if (!(refreshTokenRepo.existsByEmail(email))) {
            throw new RuntimeException("No user found");
        }
        refreshTokenRepo.deleteByEmail(email);
    }

    @Override
    public void save(User user){
        userRepository.save(user);
    }

    @Override
    public RefreshResponse refresh(String token) {

        String hashed=hashToken(token);
        RefreshToken refreshToken = refreshTokenRepo.findByToken(hashed)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (refreshToken.getExpiryDate().before(new Date())) {
            throw new RuntimeException("Refresh token expired");
        }

        String newAccessToken = jwtService.generateAccessToken(refreshToken.getEmail());
        return new RefreshResponse(newAccessToken);
    }

    // read only operations no need for @Transactional
    @Override
    public User findUserByEmail(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new RuntimeException("Not Exist");
        }
        User user=userRepository.findUserByEmail(email).orElseThrow(()->new RuntimeException("Not Found"));
        return user ;
    }

    public static String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing token", e);
        }
    }

}
