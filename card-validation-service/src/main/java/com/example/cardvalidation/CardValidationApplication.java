package com.example.cardvalidation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.example.cardvalidation",
        "com.example.common"
})
public class CardValidationApplication {
    public static void main(String[] args) {
        SpringApplication.run(CardValidationApplication.class, args);
    }
}
