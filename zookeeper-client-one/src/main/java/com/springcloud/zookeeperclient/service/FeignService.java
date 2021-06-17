package com.springcloud.zookeeperclient.service;

import com.springcloud.zookeeperclient.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author CDY
 * @date 2021/6/17
 * @description
 */
@Service
@FeignClient(value="client-one",fallback = FeignServiceHystrix.class, configuration = FeignConfiguration.class)
public interface FeignService {

    /**
     * 此处的接口路径要对应服务中要调用的接口路径
     * @param name
     * @return
     */
    @RequestMapping(value = "/clientOneView",method = RequestMethod.GET)
    String helloClientOne(@RequestParam("name")String name);

}
