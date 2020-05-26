package com.springcloud.clientzuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient
// 开启 Zuul 网关路由代理，过滤
@EnableZuulProxy
public class ClientZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientZuulApplication.class, args);
    }

}
