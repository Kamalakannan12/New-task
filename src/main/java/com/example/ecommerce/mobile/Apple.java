package com.example.ecommerce.mobile;

import org.springframework.stereotype.Component;

@Component("apple")
public class Apple extends Mobile {

    @Override
    public String getDetails() {
        return "Apple iPhone - iOS";
    }
}
