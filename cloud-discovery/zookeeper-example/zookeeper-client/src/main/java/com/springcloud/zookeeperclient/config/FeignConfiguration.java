package com.springcloud.zookeeperclient.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author CDY
 * @date 2021/6/17
 * @description
 */
@Configuration
public class FeignConfiguration {

    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }

}
