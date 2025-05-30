package com.example.merchantbank.listener;

import com.example.common.model.PaymentMessage;
import com.example.merchantbank.service.PaymentProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentProcessingListener {

    private static final Logger logger = LoggerFactory.getLogger(PaymentProcessingListener.class);
    private final PaymentProcessingService service;
    private final JmsTemplate jmsTemplate;

    public PaymentProcessingListener(PaymentProcessingService service, JmsTemplate jmsTemplate) {
        this.service = service;
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = "funds.validated", containerFactory = "jmsListenerContainerFactory")
    public void onFundsValidated(PaymentMessage msg) {
        logger.debug("🟢 [JMS Received] funds.validated, payload={}", msg);
        try {
            service.process(msg);
        } catch (Exception ex) {
            msg.setStatus("FAILED");
            msg.setErrorReason("Internal error: " + ex.getMessage());
            jmsTemplate.convertAndSend("payment.failed", msg);
        }
    }
}
