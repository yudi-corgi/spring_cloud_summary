package com.springcloud.consulconsumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author YUDI
 * @date 2020/6/14 19:24
 */
@FeignClient("consul-provider")
public interface FeignService {

    @GetMapping("/provider")
    public String callProvider(@RequestParam(value = "name",required = false) String name);

}
