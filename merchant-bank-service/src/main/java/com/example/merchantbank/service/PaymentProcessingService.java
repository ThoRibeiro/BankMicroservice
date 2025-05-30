package com.example.merchantbank.service;

import com.example.common.model.PaymentMessage;
import com.example.merchantbank.model.Merchant;
import com.example.merchantbank.repository.MerchantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentProcessingService.class);
    private final MerchantRepository merchantRepo;
    private final JmsTemplate jmsTemplate;

    public PaymentProcessingService(MerchantRepository merchantRepo, JmsTemplate jmsTemplate) {
        this.merchantRepo = merchantRepo;
        this.jmsTemplate = jmsTemplate;
    }

    public void process(PaymentMessage msg) {
        logger.debug("🔍 Processing merchant for paymentId={}, merchantId={}",
                msg.getPaymentId(), msg.getMerchantId());

        Optional<Merchant> opt = merchantRepo.findById(msg.getMerchantId());
        if (opt.isEmpty()) {
            msg.setStatus("FAILED");
            msg.setErrorReason("Unknown merchantId: " + msg.getMerchantId());
            logger.warn("⚠️ Merchant not found for paymentId={}: {}",
                    msg.getPaymentId(), msg.getErrorReason());
            logger.debug("✉️ Sending JMS to payment.failed, payload={}", msg);
            jmsTemplate.convertAndSend("payment.failed", msg);
            return;
        }

        Merchant m = opt.get();
        logger.debug("📊 Current balance={} for merchantId={}", m.getBalance(), m.getId());

        double newBalance = m.getBalance() + msg.getAmount();
        m.setBalance(newBalance);
        merchantRepo.save(m);
        logger.info("✅ Processed paymentId={} : new balance={} for merchantId={}",
                msg.getPaymentId(), newBalance, m.getId());

        msg.setStatus("PROCESSED");
        jmsTemplate.convertAndSend("payment.processed", msg);
    }
}
