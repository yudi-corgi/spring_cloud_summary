package com.cloudalibaba.sentinel.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author YUDI-Corgi
 * @description
 */
@FeignClient(name = "sentinel-provider", contextId = "echoService")
public interface EchoService {

    /**
     * 输出字符串
     * @return Hello World!
     */
    @GetMapping("/echo")
    String echo();
}
