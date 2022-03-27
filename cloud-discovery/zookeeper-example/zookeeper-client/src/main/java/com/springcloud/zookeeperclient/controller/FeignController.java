package com.springcloud.zookeeperclient.controller;

import com.springcloud.zookeeperclient.service.FeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CDY
 * @date 2021/6/17
 * @description
 */
@RestController
@Slf4j
public class FeignController {

    @Autowired
    private FeignService feignService;

    /**
     * 调用 client-one 服务的接口 clientOneView
     * @param name
     */
    @GetMapping("/zkClientOneView")
    public void clientOneView(@RequestParam("name") String name){
        String s = feignService.helloClientOne(name);
        log.info("调用 client-one 服务返回的结果：" + s);
    }

}
