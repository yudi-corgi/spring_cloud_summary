package com.springcloud.clientzuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
// 开启 Zuul 网关路由代理，过滤
@EnableZuulProxy
@RestController
public class ClientZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientZuulApplication.class, args);
    }

    @RequestMapping("/local")
    public String local(@RequestParam("name") String name){
        return "调用 Zuul 本地接口："+name;
    }

}
