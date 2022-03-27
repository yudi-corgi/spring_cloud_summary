package com.springcloud.zookeeperclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author CDY
 * @date 2021/6/17
 * @description  使用 ZK 作为注册中心，服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ZkClientOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZkClientOneApplication.class, args);
    }
}
