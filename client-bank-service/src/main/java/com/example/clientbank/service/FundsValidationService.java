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
        if (clientOpt.isEmpty()) {
            msg.setStatus("FAILED");
            msg.setErrorReason("Unknown clientId: " + msg.getClientId());
            jmsTemplate.convertAndSend("payment.failed", msg);
            return;
        }

        Client client = clientOpt.get();
        if (client.getBalance() < msg.getAmount()) {
            msg.setStatus("FAILED");
            msg.setErrorReason("Insufficient funds: balance=" + client.getBalance());
            jmsTemplate.convertAndSend("payment.failed", msg);
        } else {
            msg.setStatus("FUNDS_VALID");
            jmsTemplate.convertAndSend("funds.validated", msg);
        }
    }
}
