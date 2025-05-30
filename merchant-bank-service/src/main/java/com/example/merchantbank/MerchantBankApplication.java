package com.example.merchantbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.example.merchantbank",
        "com.example.common"
})
public class MerchantBankApplication {
    public static void main(String[] args) {
        SpringApplication.run(MerchantBankApplication.class, args);
    }
}
