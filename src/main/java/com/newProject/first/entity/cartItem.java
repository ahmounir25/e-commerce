package com.newProject.first.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class cartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public cartItem() {
    }

    public cartItem(int quantity, double price) {
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public Cart getCart() {
        return cart;
    }

    @Override
    public String toString() {
        return "cartItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", price=" + price +
                ", cart=" + cart +
                ", product=" + product +
                '}';
    }
}
