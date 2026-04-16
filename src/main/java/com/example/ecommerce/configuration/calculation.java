package com.example.ecommerce.configuration;

import com.example.ecommerce.entity.Product;
import java.util.List;

public interface calculation {
    double calculate(List<Product> products);
}
