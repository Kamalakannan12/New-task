package com.example.ecommerce.service;

import com.example.ecommerce.entity.Invoice;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.UserDetails;
import com.example.ecommerce.repository.InvoiceRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;
    @Autowired
    private PdfService pdfService;
    @Autowired
    private InvoiceRepository invoiceRepo;

    private static final Object lock = new Object();

    public Order placeOrder(Long productId, int qty,Long userid) {

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
        UserDetails user = userService.getUserById(userid).orElseThrow(() -> new RuntimeException("User not found"));


        // create order
        Order order = new Order();
        order.setProduct(product);
        order.setQuantity(qty);
        order.setUser(user);
        order.setTotalPrice(product.getPrice() * qty);
        order.setOrderDate(LocalDateTime.now());
        Order savedOrder = orderRepo.save(order);
        Invoice invoice = new Invoice();

        String invoiceNumber = "INV-" + savedOrder.getId();
        invoice.setInvoiceNumber(invoiceNumber);

        invoice.setOrder(savedOrder);
        //savedOrder.setInvoice(invoice);

        invoiceRepo.save(invoice);
       // byte[] pdfBytes = pdfService.generateInvoice(savedOrder.getId());


        String ccEmail = "kamalakannan200418@gmail.com";

       // emailService.sendOrderConfirmation(user.getEmail(), savedOrder,pdfBytes,ccEmail);

        return savedOrder;
    }

    public Order getOrdersByProductId(Long productid) {
        return orderRepo.getOrdersByProductId(productid);
    }

    public List<Order> getorderstable() {
        return orderRepo.getorderstable();
    }

    public String sendmessage() {
        List<UserDetails>userDetails=userService.getAllUsers();
        List<String> emaillist=new ArrayList<>();
        for (UserDetails mail:userDetails){
            emaillist.add(mail.getEmail());
        }
        String[] newmail=emaillist.toArray(new String[0]);
        emailService.sendallmail( newmail);

        return "Email send successfull";
    }

    public String ccmessage() {
        List<UserDetails>userDetails=userService.getAllUsers();
        List<String> emaillist=new ArrayList<>();
        for (UserDetails mail:userDetails){
            emaillist.add(mail.getEmail());
        }
        String[] newmail=emaillist.toArray(new String[0]);
        String cc="dhanush.arumugam@finsurge.ai";
        emailService.sendcc(newmail,cc);
        return "Email send successfully";
    }

    public List<Order> getorders(Long productid) {
        Product product=productRepo.getById(productid);
        List<Order> orders = product.getOrders();
        return orders;
    }
}