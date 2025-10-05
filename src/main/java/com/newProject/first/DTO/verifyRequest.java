package com.newProject.first.DTO;

import com.newProject.first.Validation.uniqueEmail;
import jakarta.validation.constraints.NotBlank;

public class verifyRequest {
    @NotBlank
    @uniqueEmail
    private String email;

    public verifyRequest(String email) {
        this.email = email;
    }

    public verifyRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
