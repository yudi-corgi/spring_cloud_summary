package com.springcloud.clientfeign.service;

import org.springframework.stereotype.Component;

/**
 * Feign 熔断器实现类
 * @author YUDI
 * @date 2020/5/26 14:32
 */
@Component
public class FeignServiceHystrix implements FeignService {
    @Override
    public String helloClientOne(String name) {
        return "Feign Error,this is fallBack method! Bye bye,"+name;
    }
}
