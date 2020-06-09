package com.springcloud.clientribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
// 基于spring-cloud-netflix，只能在 Eureka 为注册中心时作用。
// @EnableEurekaClient
// 该注解基于 Spring spring-cloud-commons，可以在 Consul、zookeeper、Eureka 为注册中心等多种场景使用
// @EnableDiscoveryClient
// 启用熔断器模式，@EnableHystrix 已包含 @EnableCircuitBreaker
// @EnableHystrix
@EnableCircuitBreaker
@EnableHystrixDashboard
public class ClientRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientRibbonApplication.class, args);
    }

    /**
     * 注册 RestTemplate 到 IOC 容器中
     * 并开启负载均衡
     * @return
     */
    @Bean
    @LoadBalanced
    // @EasyLoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
