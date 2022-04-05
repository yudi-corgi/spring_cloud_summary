package com.cloudalibaba.sentinel.api.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @author YUDI-Corgi
 * @description Feign 配置类
 */
public class FeignConfig {

    @Bean
    public Logger.Level level() {
        return Logger.Level.FULL;
    }

}
