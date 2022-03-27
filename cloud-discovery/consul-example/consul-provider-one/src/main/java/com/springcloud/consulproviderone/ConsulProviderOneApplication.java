package com.springcloud.consulproviderone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ConsulProviderOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsulProviderOneApplication.class, args);
    }

}
