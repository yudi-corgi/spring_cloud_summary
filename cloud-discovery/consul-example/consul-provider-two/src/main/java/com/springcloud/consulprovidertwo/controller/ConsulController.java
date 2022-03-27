package com.springcloud.consulprovidertwo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YUDI
 * @date 2020/6/14 18:15
 */
@RestController
public class ConsulController {

    @Value("${server.port}")
    String port;

    @GetMapping("/provider")
    public String providerService(@RequestParam(value = "name",defaultValue = "GG",required = false) String name){
        return name + ",This is provider-one service,port:"+port;
    }

}
