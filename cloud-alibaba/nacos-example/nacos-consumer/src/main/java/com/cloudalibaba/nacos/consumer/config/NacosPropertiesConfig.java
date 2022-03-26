package com.cloudalibaba.nacos.consumer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author YUDI-Corgi
 * @description Nacos 配置中心热更新配置类
 */
@Data
@Component
@ConfigurationProperties(prefix = "pattern")
public class NacosPropertiesConfig {

    private String info;
    private String env;
    private String name;
}
