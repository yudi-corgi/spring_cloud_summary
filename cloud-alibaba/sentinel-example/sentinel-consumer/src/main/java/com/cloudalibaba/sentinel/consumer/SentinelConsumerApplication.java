package com.cloudalibaba.sentinel.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author YUDI-Corgi
 * @description
 */
@EnableFeignClients(basePackages = {"com.cloudalibaba.sentinel.api.service"})
@ComponentScan(basePackages = {"com.cloudalibaba.sentinel.consumer", "com.cloudalibaba.sentinel.api.service"})
@SpringBootApplication
public class SentinelConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelConsumerApplication.class, args);
    }
}
