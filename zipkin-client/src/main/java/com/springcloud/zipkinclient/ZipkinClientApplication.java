package com.springcloud.zipkinclient;

import brave.sampler.Sampler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class ZipkinClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinClientApplication.class, args);
    }

    private static final Logger log = Logger.getLogger(ZipkinClientApplication.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @RequestMapping("/callZipkinTwo")
    public String callZipkinTwo(){
        log.log(Level.INFO, "调用 zipkin-client-two.");
        return restTemplate.getForObject("http://zipkin-client-two/getZipkinOneCall", String.class);
    }

    @RequestMapping("/getZipkinTwoCall")
    public String info(){
        log.log(Level.INFO, "调用 zipkin-client-one 成功.");
        return "调用 zipkin-client-one 成功.";
    }

    @Bean
    public Sampler defaultSampler(){
        return Sampler.ALWAYS_SAMPLE;
    }

}
