package com.example.clientbank.listener;

import com.example.clientbank.service.FundsValidationService;
import com.example.common.model.PaymentMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class FundsValidationListener {

    private final FundsValidationService service;

    public FundsValidationListener(FundsValidationService service) {
        this.service = service;
    }

    @JmsListener(destination = "card.validated", containerFactory = "jmsListenerContainerFactory")
    public void onCardValidated(PaymentMessage msg) {
        service.process(msg);
    }
}
