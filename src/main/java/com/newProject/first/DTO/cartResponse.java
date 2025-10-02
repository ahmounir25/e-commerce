package com.newProject.first.DTO;

import com.newProject.first.entity.cartItem;

import java.util.List;

public class cartResponse {
    private int id;
    private String email;
    private double totalAmount;
//    private List<itemResponse>items;

    public cartResponse(int id, String email,double totalAmount) {
        this.id = id;
        this.email = email;
        this.totalAmount=totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
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

}
