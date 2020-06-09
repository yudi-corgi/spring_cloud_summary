package com.springcloud.clientribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 服务层
 * @author YUDI
 * @date 2020/5/26 11:50
 */
@Service
public class RibbonService {

    @Autowired
    private RestTemplate restTemplate;

    // 该注解指定服务熔断后调用的本地 fallBack 方法，并会把参数传递给该方法
    @HystrixCommand(fallbackMethod = "fallBackMethod",commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy",value = "THREAD"),
            @HystrixProperty(name = "", value = "")
    })
    public String helloService(String name){
        //调用服务方法，地址填写注册的服务名称，Eureka 会自动转换为实际的 URL 地址
        return restTemplate.getForObject("http://client-one/clientOneView?name="+name,String.class);
    }

    public String fallBackMethod(String name){
        return "Ribbon Error,this is fallBack method! Bye bye,"+name;
    }

}
