package com.newProject.first.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class orderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private  Order order;

    public orderItem() {
    }

    public orderItem(int quantity, double price) {
        this.quantity = quantity;
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
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

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "orderItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", price=" + price +
                ", product=" + product +
                ", order=" + order +
                '}';
    }


}
