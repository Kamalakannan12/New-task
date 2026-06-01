package com.example.ecommerce.client;

import com.example.ecommerce.Dto.PaymentRequest;
import com.example.ecommerce.Dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service")
public interface PaymentClient {

    @PostMapping("/payment/makepayment")
    public PaymentResponse makePayment(@RequestBody PaymentRequest request);
}
