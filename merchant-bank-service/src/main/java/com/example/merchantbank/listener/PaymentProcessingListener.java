package com.example.merchantbank.listener;

import com.example.common.model.PaymentMessage;
import com.example.merchantbank.service.PaymentProcessingService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentProcessingListener {

    private final PaymentProcessingService service;

    public PaymentProcessingListener(PaymentProcessingService service) {
        this.service = service;
    }

    @JmsListener(destination = "funds.validated", containerFactory = "jmsListenerContainerFactory")
    public void onFundsValidated(PaymentMessage msg) {
        service.process(msg);
    }
}
