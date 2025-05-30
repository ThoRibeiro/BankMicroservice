package com.example.gateway.controller;

import com.example.gateway.model.PaymentRequest;
import com.example.gateway.model.PaymentResponse;
import com.example.gateway.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @RequestBody PaymentRequest request) {

        String paymentId = paymentService.initiatePayment(request);
        PaymentResponse response = new PaymentResponse(paymentId, "REQUEST");
        return ResponseEntity.ok(response);
    }
}
