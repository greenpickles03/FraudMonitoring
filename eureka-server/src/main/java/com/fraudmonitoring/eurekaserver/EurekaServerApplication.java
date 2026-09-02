package com.fraudmonitoring.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    //This Service Descovery
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

}
