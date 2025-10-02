package com.newProject.first.DTO;

import com.newProject.first.Validation.uniqueEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {
    @NotBlank(message = "required")
    @uniqueEmail
    private String email;

    @NotBlank(message = "required")
    @Size(min = 6,message = "at least 6 Character")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

