package com.cloudalibaba.nacos.consumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author YUDI-Corgi
 * @description
 */
@Slf4j
@RestController
public class TestController {

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/echo")
    public void echo() {
        String content = restTemplate.getForObject("http://localhost:8858/echo", String.class);
        log.info(content);
    }
}
