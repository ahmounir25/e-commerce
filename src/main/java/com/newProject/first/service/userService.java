package com.newProject.first.service;

import com.newProject.first.DTO.*;
import com.newProject.first.entity.Cart;
import com.newProject.first.entity.User;
import com.newProject.first.entity.cartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface userService {
    void register(RegisterRequest request);
    LoginResponse login(LoginRequest loginRequest);
    void logout(String email);
    RefreshResponse refresh(String token);
    User findUserByEmail(String email);
    void save(User user);
    void reSendVerification(verifyRequest request);

}
