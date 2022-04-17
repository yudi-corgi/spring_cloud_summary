package com.cloudalibaba.seata.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author YUDI-Corgi
 * @description Account 服务启动类
 */
@SpringBootApplication
@MapperScan("com.cloudalibaba.seata.account.mapper")
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

}
