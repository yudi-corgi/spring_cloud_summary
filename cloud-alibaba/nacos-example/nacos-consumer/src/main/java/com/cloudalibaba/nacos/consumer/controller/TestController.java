package com.cloudalibaba.nacos.consumer.controller;

import com.cloudalibaba.nacos.consumer.config.NacosPropertiesConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author YUDI-Corgi
 * @description
 */
@Slf4j
@RestController
@RefreshScope
public class TestController {

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private NacosPropertiesConfig nacosPropertiesConfig;
    @Value("${pattern.dateFormat}")
    private String dateFormat;

    @GetMapping("/echo")
    public void echo() {
        String content = restTemplate.getForObject("http://nacos-provider/echo", String.class);
        log.info(content);
    }

    @GetMapping("/now")
    public String now() {
        log.info("通过 @RefreshScope 热更新配置信息：" + dateFormat);
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateFormat));
    }

    @GetMapping("/info")
    public String info() {
        log.info("通过 @ConfigurationProperties 热更新配置信息：" + nacosPropertiesConfig.getInfo());
        return nacosPropertiesConfig.getInfo();
    }

    @GetMapping("/env")
    public NacosPropertiesConfig env() {
        log.info("多环境配置共享：" + nacosPropertiesConfig.getEnv());
        return nacosPropertiesConfig;
    }
}
