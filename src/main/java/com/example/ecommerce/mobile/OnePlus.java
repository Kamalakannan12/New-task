package com.example.ecommerce.mobile;


import org.springframework.stereotype.Component;

@Component("oneplus")
public class OnePlus extends Mobile {

    @Override
    public String getDetails() {
        return "OnePlus - Android,No 1 Brand";
    }
}
