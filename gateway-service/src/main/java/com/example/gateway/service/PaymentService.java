package com.example.gateway.service;

import com.example.common.model.PaymentMessage;
import com.example.gateway.model.PaymentRequest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    private final JmsTemplate jmsTemplate;

    public PaymentService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public String initiatePayment(PaymentRequest request) {
        String paymentId = UUID.randomUUID().toString();

        PaymentMessage msg = new PaymentMessage();
        msg.setPaymentId(paymentId);
        msg.setAmount(request.getAmount());
        msg.setCurrency(request.getCurrency());
        msg.setCardNumber(request.getCardNumber());
        msg.setExpiryDate(request.getExpiryDate());
        msg.setClientId(request.getClientId());
        msg.setMerchantId(request.getMerchantId());
        msg.setStatus("REQUEST");

        jmsTemplate.convertAndSend("payment.request", msg);

        return paymentId;
    }
}
