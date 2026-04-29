package com.example.ecommerce.entity;

import com.example.ecommerce.configuration.DateTime;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@JsonPropertyOrder({"id", "name", "price", "quantity", "Amount", "Tax", "Total_Amount", "createdAt"})
@Entity
public class Product extends DateTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private int quantity;
    private String brand;
    private String Description;
    private LocalDateTime createdAt;
    private String imagePath;
    private double Amount;
    private double Tax;
    private double Total_Amount;
    //private String email;


    @Transient
    private String imageUrl;

    public double getTax() {
        return Tax;
    }

    public void setTax(double tax) {
        Tax = tax;
    }

    public double getTotal_Amount() {
        return Total_Amount;
    }

    public void setTotal_Amount(double total_Amount) {
        Total_Amount = total_Amount;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public String getImageUrl() {
        if (imagePath == null) return null;
        return "http://localhost:8080/products/image/" + imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    // Constructors
    public Product() {}

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getBrand() {return brand;}
    public void setBrand(String brand) {this.brand = brand;}

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
