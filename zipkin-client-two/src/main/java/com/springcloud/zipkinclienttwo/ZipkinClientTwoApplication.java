package com.springcloud.zipkinclienttwo;

import brave.sampler.Sampler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class ZipkinClientTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinClientTwoApplication.class, args);
    }

    private static final Logger log = Logger.getLogger(ZipkinClientTwoApplication.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @RequestMapping("getZipkinOneCall")
    public String getZipkinOneCall(){
        log.info("获取 zipkinOneCall 成功，调用 zipkin-client-one 服务.");
        return restTemplate.getForObject("http://zipkin-client-one/getZipkinTwoCall",String.class);
    }

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

}
