package com.newProject.first.DTO;

import java.util.List;

public class ordersResponse {

    private int id;
    private String email;
    private double totalAmount;
    private List<itemResponse> items;

    public ordersResponse(int id, String email,double totalAmount ,List<itemResponse> items) {
        this.id = id;
        this.email = email;
        this.items = items;
        this.totalAmount=totalAmount;
    }

    public ordersResponse() {

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

    public List<itemResponse> getItems() {
        return items;
    }

    public void setItems(List<itemResponse> items) {
        this.items = items;
    }
}
