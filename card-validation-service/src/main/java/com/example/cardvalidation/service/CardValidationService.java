package com.example.cardvalidation.service;

import com.example.common.model.PaymentMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Service
public class CardValidationService {

    private final JmsTemplate jmsTemplate;

    public CardValidationService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void validate(PaymentMessage msg) {
        boolean validFormat = msg.getCardNumber() != null
                && msg.getCardNumber().matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}");

        boolean notExpired = false;
        try {
            YearMonth expiry = YearMonth.parse(msg.getExpiryDate(), DateTimeFormatter.ofPattern("MM/yy"));
            notExpired = expiry.isAfter(YearMonth.now());
        } catch (Exception e) {
            notExpired = false;
        }

        if (validFormat && notExpired) {
            msg.setStatus("CARD_VALID");
            jmsTemplate.convertAndSend("card.validated", msg);
        } else {
            msg.setStatus("FAILED");
            jmsTemplate.convertAndSend("payment.failed", msg);
        }
    }
}
