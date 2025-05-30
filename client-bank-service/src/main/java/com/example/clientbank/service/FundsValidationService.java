package com.example.clientbank.service;

import com.example.clientbank.model.Client;
import com.example.clientbank.repository.ClientRepository;
import com.example.common.model.PaymentMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FundsValidationService {

    private final ClientRepository clientRepo;
    private final JmsTemplate jmsTemplate;

    public FundsValidationService(ClientRepository clientRepo, JmsTemplate jmsTemplate) {
        this.clientRepo = clientRepo;
        this.jmsTemplate = jmsTemplate;
    }

    public void process(PaymentMessage msg) {
        Optional<Client> clientOpt = clientRepo.findById(msg.getClientId());
        boolean ok = clientOpt.map(c -> c.getBalance() >= msg.getAmount())
                .orElse(false);

        if (ok) {
            msg.setStatus("FUNDS_VALID");
            jmsTemplate.convertAndSend("funds.validated", msg);
        } else {
            msg.setStatus("FAILED");
            jmsTemplate.convertAndSend("payment.failed", msg);
        }
    }
}
