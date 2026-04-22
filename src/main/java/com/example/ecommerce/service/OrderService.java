package com.example.ecommerce.service;

import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private OrderRepository orderRepo;

    private static final Object lock = new Object();

    public Order placeOrder(Long productId, int qty) {

        Product product = productRepo.findById(productId).orElse(null);

        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        synchronized (lock) {

            if (product.getQuantity() < qty) {
                throw new RuntimeException("Out of stock");
            }

            product.setQuantity(product.getQuantity() - qty);
            productRepo.save(product);
        }

        // create order
        Order order = new Order();
        order.setproductid(product.getId());
        order.setProductName(product.getName());
        order.setQuantity(qty);
        order.setTotalPrice(product.getPrice() * qty);
        order.setOrderDate(LocalDateTime.now());

        return orderRepo.save(order);
    }

    public Order getOrdersByProductId(Long productid) {
        return orderRepo.getOrdersByProductId(productid);
    }

    public List<Order> getorderstable() {
        return orderRepo.getorderstable();
    }
}