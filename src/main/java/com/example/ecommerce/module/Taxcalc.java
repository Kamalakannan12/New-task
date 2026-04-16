package com.example.ecommerce.module;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.configuration.calculation;
import org.springframework.stereotype.Component;
import java.util.List;

@Component("tax")
public class Taxcalc implements calculation {

    @Override
    public double calculate(List<Product> products) {
        double subtotal = 0;

        for (Product p : products) {
            subtotal += p.getPrice() * p.getQuantity();
        }

        return subtotal * 0.18; // 18% GST
    }
}
