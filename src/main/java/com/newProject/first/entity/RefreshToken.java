package com.newProject.first.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "email")
    private String email;
    @Column(name = "token")
    private String token;
    @Column(name = "expiry_date")
    private Date expiryDate;

    public RefreshToken() {
    }

    public RefreshToken(String email, String token, Date expiryDate) {
        this.email = email;
        this.token = token;
        this.expiryDate = expiryDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "RefreshToken{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", token='" + token + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
