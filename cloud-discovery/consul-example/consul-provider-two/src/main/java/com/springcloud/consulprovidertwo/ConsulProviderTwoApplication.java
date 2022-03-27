package com.springcloud.consulprovidertwo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ConsulProviderTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsulProviderTwoApplication.class, args);
    }

}
