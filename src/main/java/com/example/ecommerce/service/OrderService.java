package com.example.ecommerce.service;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.OrderRepository;
import java.time.LocalDateTime;

public Order placeOrder(int productId, int qty) {

    Product product = ProductRepository.findById(productId);

    if (product == null) {
        throw new RuntimeException("Product not found");
    }

    if (product.getQuantity() < qty) {
        throw new RuntimeException("Out of stock");
    }
    synchronized (this) {
        product.setQuantity(product.getQuantity() - qty);
    }

    Order order = new Order();
    order.setProductName(product.getName());
    order.setQuantity(qty);
    order.setTotalPrice(product.getPrice() * qty);
    order.setOrderDate(LocalDateTime.now());

    OrderRepository.save(order);

    return order;
}
