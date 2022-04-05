package com.cloudalibaba.nacos.api.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @author YUDI-Corgi
 * @description Feign 自定义配置类
 */
public class FeignConfig {

    /**
     * 修改 Feign 日志级别
     * @return {@link Logger.Level}
     */
    @Bean
    public Logger.Level logLevel() {
        return Logger.Level.BASIC;
    }

    /* 修改 Feign 编解码器
    @Bean
    public Decoder decoder() {
        return new FeignCodec();
    }

    @Bean
    public Encoder encoder() {
        return new FeignCodec();
    }
    */
}
