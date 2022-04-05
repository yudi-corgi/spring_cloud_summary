package com.cloudalibaba.sentinel.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author YUDI-Corgi
 * @description
 */
@EnableFeignClients(basePackages = "com.cloudalibaba.sentinel.api.service")
@SpringBootApplication
public class SentinelConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelConsumerApplication.class, args);
    }
}
