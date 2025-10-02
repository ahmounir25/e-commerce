package com.newProject.first.DTO;

import com.newProject.first.Validation.uniqueEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank(message = "required")
    private String firstName;
    @NotBlank(message = "required")
    private String lastName;
    @NotBlank(message = "required")
    @uniqueEmail
    private String email;
    @NotBlank(message = "required")
    @Size(min = 6 ,message = "at least 6 Characters")
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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
