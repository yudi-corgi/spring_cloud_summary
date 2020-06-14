package com.springcloud.consulconsumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author YUDI
 * @date 2020/6/14 19:27
 */
@Service
public class FeignServiceImpl {

    @Autowired
    private FeignService feignService;

    public String callProvider(String name){
        return feignService.callProvider(name);
    }

}
