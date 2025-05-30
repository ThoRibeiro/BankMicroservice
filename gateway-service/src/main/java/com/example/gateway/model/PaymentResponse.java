package com.example.gateway.model;

public class PaymentResponse {
    private String paymentId;
    private String status;

    public PaymentResponse(String paymentId, String status) {
        this.paymentId = paymentId;
        this.status = status;
    }

    //getters
    public String getPaymentId() { return paymentId; }
    public String getStatus() { return status; }
}
