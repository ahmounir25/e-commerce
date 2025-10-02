package com.newProject.first.controller;

import com.newProject.first.DTO.*;
import com.newProject.first.entity.User;
import com.newProject.first.service.JwtService;
import com.newProject.first.service.userService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class userController {
    private userService userService;
    private JwtService jwtService;

    @Autowired
    public userController(userService userService,JwtService jwtService) {
        this.userService = userService;
        this.jwtService=jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse tokens = userService.login(loginRequest);
            return ResponseEntity.ok(tokens);
        }catch (Exception ex){
            if (ex.getMessage().contains("Credentials")){
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader(name = "Authorization") String authHeader ) {
        try {
            String email=jwtService.getEmail(authHeader);
            userService.logout(email);
            return ResponseEntity.status(HttpStatus.OK).body("LoggedOut Successfully");
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest refreshRequest) {
        try {
            RefreshResponse refreshResponse = userService.refresh(refreshRequest.getRefreshToken());
            return ResponseEntity.ok(refreshResponse);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }
//TODO: CENTRALIZE THE OPERATION OF GETTING CLAIMS AND SUBJECT return DTO //Done
    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestHeader(name = "Authorization") String authHeader) {
        if (authHeader==null||!authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid auth header");
        }
        try {
            String email=jwtService.getEmail(authHeader);
            User myUser=userService.findUserByEmail(email);
            userResponse response=new userResponse(myUser.getId(),myUser.getFirstName(),myUser.getLastName(),myUser.getEmail());
            return  ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }

    }

}
