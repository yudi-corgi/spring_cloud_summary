package com.springcloud.configserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
// 表示将当前服务注册为服务配置中心的服务端
@EnableConfigServer
@EnableEurekaClient
@RestController
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.application.name}")
    private String serviceId;

    @PostMapping("/refresh")
    public Object refreshConfig(){
        Map<String,Integer> map = new HashMap<>();
        // 根据服务名称读取注册服务的信息.
        ServiceInstance instance = loadBalancerClient.choose(serviceId);
        System.out.println("配置中心名称："+serviceId);
        //刷新请求的地址：config-client 的地址
        String url = "http://localhost:8091/actuator/bus-refresh";
        System.out.println("配置刷新请求地址："+ url);
        restTemplate.postForObject(url,map,Object.class);
        map.put("code",0);
        return map;

    }

}
