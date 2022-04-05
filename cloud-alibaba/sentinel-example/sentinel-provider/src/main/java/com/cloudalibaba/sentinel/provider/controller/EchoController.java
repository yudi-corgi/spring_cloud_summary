package com.cloudalibaba.sentinel.provider.controller;

import com.cloudalibaba.sentinel.api.service.EchoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author YUDI-Corgi
 * @description 此类多余...
 */
@RestController
public class EchoController {

    @Resource
    private EchoService echoService;

    @GetMapping("/echo")
    public String echo() {
        return echoService.echo();
    }

}