package com.springcloud.consulconsumer.controller;

import com.springcloud.consulconsumer.service.FeignServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author YUDI
 * @date 2020/6/14 18:10
 */
@RestController
public class ConsumerController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private FeignServiceImpl feignService;

    /**
     * 获取指定 serviceId 的所有服务
     * @return
     */
    @RequestMapping("/services")
    public Object services(){
        return discoveryClient.getInstances("consul-provider");
    }

    /**
     * 从指定的 serviceId 选择一个服务(轮询)
     * @return
     */
    @RequestMapping("/discover")
    public Object discover(){
        return loadBalancerClient.choose("consul-provider").getUri().toString();
    }

    @RequestMapping("/call")
    public String callProvider(){
        ServiceInstance instance = loadBalancerClient.choose("consul-provider");
        System.out.println("服务地址："+ instance.getHost());
        System.out.println("服务端口："+ instance.getPort());
        System.out.println("服务名称："+ instance.getServiceId());

        String callServiceResult = new RestTemplate().getForObject(instance.getUri().toString()+"/provider",String.class);
        System.out.println("调用结果："+callServiceResult);
        return callServiceResult;
    }

    @RequestMapping("/feignCall")
    public String feignCall(@RequestParam(value = "name",required = false)String name){
        String result = feignService.callProvider(name);
        System.out.println(result);
        return result;
    }
}
