package com.example.ecommerce.Dto;

public class PaymentResponse {
    public String getPaymentLink() {
        return PaymentLink;
    }

    public void setPaymentLink(String paymentLink) {
        PaymentLink = paymentLink;
    }

    private String PaymentLink;

    public String getRazorpayOrderId() {
        return RazorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        RazorpayOrderId = razorpayOrderId;
    }

    private String RazorpayOrderId;
}
