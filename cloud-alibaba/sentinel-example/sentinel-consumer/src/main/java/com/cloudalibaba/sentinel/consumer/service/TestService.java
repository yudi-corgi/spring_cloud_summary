package com.cloudalibaba.sentinel.consumer.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.cloudalibaba.sentinel.api.service.EchoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author YUDI-Corgi
 * @description
 */
@Service
public class TestService {

    @Resource
    private EchoService echoService;
    @Resource
    private EasyService easyService;

    @SentinelResource("laugh")
    public String laugh(int i) {
        System.out.println(echoService.echo(String.valueOf(i)));
        /*
            这里调用其它服务是为了验证 Sentinel 簇点链路是否会嵌套记录同个对象里的资源，
            验证后是不会嵌套，比如：
                subLaugh() 是当前 TestService 的方法，那么簇点链路就只会显示到 laugh 这一层，
                而通过其它对象(EasyService)调用，则会显示到 subLaugh/sub2（EasyService 两个方法的资源名称）
         */
        if (i == 1) {
            return easyService.subLaugh();
        } else {
            return easyService.subLaugh2();
        }
    }

}
