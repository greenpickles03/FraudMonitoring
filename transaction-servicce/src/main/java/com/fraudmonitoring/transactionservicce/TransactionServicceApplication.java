package com.fraudmonitoring.transactionservicce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TransactionServicceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionServicceApplication.class, args);
    }

}
