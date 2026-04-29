package com.example.ecommerce.controller;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;
    @PostMapping
    public Order placeOrder(@RequestParam Long productId,
                            @RequestParam int qty,@RequestParam String email) {
        return service.placeOrder(productId, qty,email);
    }
    @PostMapping("/sendmail")
    public String sendmail(){
        return service.sendmessage();
    }
    @PostMapping("/mailwithcc")
    public String sendmailwithcc(){
        return  service.ccmessage();
    }

    @GetMapping("/product")
    public Order getOrders(@RequestParam Long productid) {
        return service.getOrdersByProductId(productid);
    }
    @GetMapping("/allorders")
    public List<Order> getallorders(){
        return service.getorderstable();
    }
}
