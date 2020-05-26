package com.springcloud.clientfeign.controller;

import com.springcloud.clientfeign.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YUDI
 * @date 2020/5/26 12:16
 */
@RestController
public class FeignController {

    @Autowired
    private FeignService feignService;

    @GetMapping("/feignClient")
    public String feignClient(@RequestParam("name")String name){
        return feignService.helloClientOne(name);
    }
}
