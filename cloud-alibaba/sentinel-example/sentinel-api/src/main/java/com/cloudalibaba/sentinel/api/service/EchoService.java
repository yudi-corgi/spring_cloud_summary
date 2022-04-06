package com.cloudalibaba.sentinel.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YUDI-Corgi
 * @description
 */
@RestController
@FeignClient(name = "sentinel-provider", contextId = "echoService", fallbackFactory = EchoFallbackFactory.class)
public interface EchoService {

    /**
     * 输出字符串
     * @param id 入参
     * @return Hello World!
     */
    @GetMapping("/echo")
    String echo(@RequestParam("id") String id);

    /**
     * draw
     * @return 字符串
     */
    @GetMapping("/draw")
    String draw();
}
