package com.springcloud.consulproviderone.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YUDI
 * @date 2020/6/14 18:01
 */
@RestController
@RefreshScope   // 动态刷新配置文件的内容
public class ConsulController {

    @Value("${server.port}")
    String port;

    @Value("${config.name}")
    String name;

    @GetMapping("/provider")
    public String providerService(@RequestParam(value = "name",defaultValue = "GG",required = false) String name){
        return name + ",This is provider-one service,port:"+port;
    }

    @GetMapping("/getConfig")
    public String getConfig(){
        return "Consul config is "+ name;
    }
}
