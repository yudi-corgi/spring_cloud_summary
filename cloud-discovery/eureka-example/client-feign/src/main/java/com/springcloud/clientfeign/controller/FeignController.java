package com.springcloud.clientfeign.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.springcloud.clientfeign.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YUDI
 * @date 2020/5/26 12:16
 */
@RestController
@DefaultProperties(defaultFallback = "globalFallback")
public class FeignController {

    @Autowired
    @Qualifier("feignServiceHystrix")
    private FeignService feignService;

    @GetMapping("/feignClient")
    public String feignClient(@RequestParam("name")String name){
        return feignService.helloClientOne(name);
    }

    @GetMapping("/defaultFallback")
    @HystrixCommand
    public String defaultFallback(@RequestParam("id") String id){
        int a = 10/0;
        return Thread.currentThread().getName() + "-ID:"+id;
    }

    public String globalFallback(){
        return "系统异常，请稍后再试试";
    }
}
