package com.example.cardvalidation.listener;

import com.example.cardvalidation.service.CardValidationService;
import com.example.common.model.PaymentMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class CardValidationListener {

    private final CardValidationService service;

    public CardValidationListener(CardValidationService service) {
        this.service = service;
    }

    @JmsListener(destination = "payment.request", containerFactory = "jmsListenerContainerFactory")
    public void onPaymentRequest(PaymentMessage msg) {
        service.validate(msg);
    }
}
