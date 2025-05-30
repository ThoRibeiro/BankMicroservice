package com.example.notification.service;

import com.example.common.model.PaymentMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void notifySuccess(PaymentMessage msg) {
        logger.info("✅ Payment processed [{}] for client {} and merchant {}: amount {} {}",
                msg.getPaymentId(), msg.getClientId(), msg.getMerchantId(),
                msg.getAmount(), msg.getCurrency());
    }

    public void notifyFailure(PaymentMessage msg) {
        logger.warn("❌ Payment failed [{}] for client {} merchant {}: reason={} status={}",
                msg.getPaymentId(), msg.getClientId(), msg.getMerchantId(),
                msg.getErrorReason(), msg.getStatus());
    }
}
