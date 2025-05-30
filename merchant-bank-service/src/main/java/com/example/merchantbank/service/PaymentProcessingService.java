package com.example.merchantbank.service;

import com.example.common.model.PaymentMessage;
import com.example.merchantbank.model.Merchant;
import com.example.merchantbank.repository.MerchantRepository;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentProcessingService {

    private final MerchantRepository merchantRepo;
    private final JmsTemplate jmsTemplate;

    public PaymentProcessingService(MerchantRepository merchantRepo, JmsTemplate jmsTemplate) {
        this.merchantRepo = merchantRepo;
        this.jmsTemplate = jmsTemplate;
    }

    public void process(PaymentMessage msg) {
        Optional<Merchant> opt = merchantRepo.findById(msg.getMerchantId());
        if (opt.isPresent()) {
            Merchant m = opt.get();
            m.setBalance(m.getBalance() + msg.getAmount());
            merchantRepo.save(m);

            msg.setStatus("PROCESSED");
            jmsTemplate.convertAndSend("payment.processed", msg);
        } else {
            msg.setStatus("FAILED");
            jmsTemplate.convertAndSend("payment.failed", msg);
        }
    }
}
