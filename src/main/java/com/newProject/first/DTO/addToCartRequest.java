package com.newProject.first.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class addToCartRequest {
    @NotNull
    private int product_id;

    @Positive
    @NotNull
    private int quantity;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
