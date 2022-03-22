package com.cloudalibaba.nacos.provider.controller;

import com.cloudalibaba.nacos.provider.service.impl.EchoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author YUDI-Corgi
 * @description
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
