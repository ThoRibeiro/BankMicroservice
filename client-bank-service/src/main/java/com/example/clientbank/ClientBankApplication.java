package com.example.clientbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.example.clientbank",
        "com.example.common"
})
public class ClientBankApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientBankApplication.class, args);
    }
}
