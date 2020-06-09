package com.springcloud.clientribbon.controller;

import com.springcloud.clientribbon.service.RibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 控制层
 * @author YUDI
 * @date 2020/5/26 11:52
 */
@Controller
public class RibbonController {

    @Autowired
    private RibbonService ribbonService;
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/ribbonClient")
    @ResponseBody
    public String hello(@RequestParam("name")String name){
        // ServiceInstance instance = loadBalancerClient.choose("localhost:8082");
        // System.out.println(instance.getHost() + ":" + instance.getPort());
        // System.out.println(instance);
        return ribbonService.helloService(name);
    }

}
