package com.example.ecommerce.module;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.configuration.calculation;
import org.springframework.stereotype.Component;
import java.util.List;

@Component("subtotal")
public class Totalcalc implements calculation{

    @Override
    public double calculate(List<Product> products) {
        double total = 0;

        for (Product p : products) {
            total += p.getPrice() * p.getQuantity();
        }

        return total;
    }
}
