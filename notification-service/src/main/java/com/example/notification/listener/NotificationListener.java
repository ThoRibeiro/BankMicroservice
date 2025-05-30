package com.example.notification.listener;

import com.example.common.model.PaymentMessage;
import com.example.notification.service.NotificationService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    private final NotificationService notificationService;

    public NotificationListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @JmsListener(destination = "payment.processed", containerFactory = "jmsListenerContainerFactory")
    public void onPaymentProcessed(PaymentMessage msg) {
        notificationService.notifySuccess(msg);
    }

    @JmsListener(destination = "payment.failed", containerFactory = "jmsListenerContainerFactory")
    public void onPaymentFailed(PaymentMessage msg) {
        notificationService.notifyFailure(msg);
    }
}
