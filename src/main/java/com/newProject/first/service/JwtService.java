package com.newProject.first.service;

import com.newProject.first.DAO.userRepository;
import com.newProject.first.entity.Role;
import com.newProject.first.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.expiryAccessToken}")
    private long expirationTimeForAccess;
    @Value("${jwt.expiryRefreshToken}")
    private long expirationTimeForRefresh;

    private final SecretKey secretKey;
    private userRepository userRepository;

    public JwtService(SecretKey secretKey, userRepository userRepository) {
        this.secretKey = secretKey;
        this.userRepository = userRepository;
    }

    public String generateVerifyToken(String email) {
        return Jwts.builder().
                setSubject(email).
                setIssuedAt(new Date()).
                setExpiration(new Date((System.currentTimeMillis() + expirationTimeForAccess))).
                signWith(secretKey, SignatureAlgorithm.HS256).compact();
    }

    public String generateAccessToken(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow();

        return Jwts.builder().
                setSubject(email).
                claim("roles", user.getRoles().stream().map(Role::getRole).toList()).
                setIssuedAt(new Date()).
                setExpiration(new Date((System.currentTimeMillis() + expirationTimeForAccess))).
                signWith(secretKey, SignatureAlgorithm.HS256).compact();
    }

    public String generateRefreshToken(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow();
        return Jwts.builder().setSubject(email).
                claim("roles", user.getRoles().stream().map(Role::getRole).toList()).
                setIssuedAt(new Date()).
                setExpiration(new Date((System.currentTimeMillis() + expirationTimeForRefresh))).
                signWith(secretKey, SignatureAlgorithm.HS256).compact();
    }

    // get Real-Content of the token
    public Claims parseToken(String token) {
        return Jwts.parserBuilder().
                setSigningKey(secretKey).
                build().
                parseClaimsJws(token).
                getBody();
    }

    public  String getEmail(String authHeader){
        String token=authHeader.substring(7);
        String email=parseToken(token).getSubject();
        return email;
    }
    public  String getVerficationEmail(String token){
        String email=parseToken(token).getSubject();
        return email;
    }


}
