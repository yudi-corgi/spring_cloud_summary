package com.springcloud.zipkinclienttwo;

import brave.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@SpringBootApplication
@RestController
@EnableHystrix
@EnableDiscoveryClient
public class ZipkinClientTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinClientTwoApplication.class, args);
    }

    private static final Logger log = Logger.getLogger(ZipkinClientTwoApplication.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Tracer tracer;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    // @HystrixCommand(fallbackMethod = "fallbackError")
    @RequestMapping("/getZipkinOneCall")
    public String getZipkinOneCall(){
        log.info("获取 zipkinOneCall 成功，调用 zipkin-client-one 服务.");
        tracer.currentSpan().tag("zipkin-two","test");
        return restTemplate.getForObject("http://localhost:8093/getZipkinTwoCall",String.class);
    }

    // 本地 fallback 方法
    public String fallbackError(){
        return "zipkin-client-two 调用 zipkin-client-one 错误";
    }

}
