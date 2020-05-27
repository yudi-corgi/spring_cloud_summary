package com.springcloud.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
@EnableEurekaClient
@RestController
@RefreshScope   //该注解表示该配置类需要刷新
public class ConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }

    @Value("${config}")
    String config;

    @RequestMapping("/configClient")
    public String getConfig(){
        return "读取到的配置 -> config:"+new String(config.getBytes(), StandardCharsets.UTF_8);
    }

}
