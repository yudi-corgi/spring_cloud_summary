package com.cloudalibaba.sentinel.consumer.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.cloudalibaba.sentinel.api.service.EchoService;
import com.cloudalibaba.sentinel.consumer.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author YUDI-Corgi
 * @description
 */
@RestController
public class ConsumeController {

    @Resource
    private EchoService echoService;
    @Resource
    private TestService testService;

    @GetMapping("/echo/{id}")
    public String echo(@PathVariable("id") int id) {
        return echoService.echo(id);
    }

    @GetMapping("/say")
    public String say() {
        return "Good luck!";
    }

    /*
        这里两个 API 相同是为了验证 Sentinel【流控规则-流控模式-链路】该规则是否仅对一级链路有效，
        验证后是只对一级链路作为入口资源才生效，若以两个 API 调用的方法作为入口资源，并不会执行流控
     */

    @SentinelResource("hotSpot")
    @GetMapping("/laugh/{i}")
    public String laugh(@PathVariable("i") int i) {
        return testService.laugh(i);
    }

    @GetMapping("/lol/{i}")
    public String lol(@PathVariable("i") int i) {
        return testService.laugh(i);
    }
}
