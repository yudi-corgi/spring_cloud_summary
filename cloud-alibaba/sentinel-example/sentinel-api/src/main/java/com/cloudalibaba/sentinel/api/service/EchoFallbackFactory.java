package com.cloudalibaba.sentinel.api.service;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author YUDI-Corgi
 * @description echoService 降级工厂
 */
@Slf4j
@Component
public class EchoFallbackFactory implements FallbackFactory<EchoService> {

    @Override
    public EchoService create(Throwable cause) {
        return new EchoService() {
            @Override
            public String echo(int id) {
                log.error("Throw exception:", cause);
                return "echo RPC error, fallback!!!";
            }

            @Override
            public String draw() {
                log.error("Throw exception:", cause);
                return "draw RPC error, fallback!!!";
            }
        };
    }
}
