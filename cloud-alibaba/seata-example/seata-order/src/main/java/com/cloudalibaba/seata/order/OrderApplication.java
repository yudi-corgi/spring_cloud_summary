package com.cloudalibaba.seata.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author YUDI-Corgi
 * @description Order 服务启动类
 */
@SpringBootApplication
@MapperScan("com.cloudalibaba.seata.order.mapper")
@EnableFeignClients(basePackages = "com.cloudalibaba.seata.order.client")
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
