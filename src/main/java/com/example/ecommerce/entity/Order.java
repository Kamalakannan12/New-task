package com.example.ecommerce.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long productid;
    private String productName;
    private double totalPrice;
    private LocalDateTime orderDate;

    public long getproductid() {
        return productid;
    }

    public void setproductid(long productid) {
        this.productid = productid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    private int quantity;

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



}
