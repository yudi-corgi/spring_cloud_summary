package com.cloudalibaba.nacos.provider.service.impl;

import org.springframework.stereotype.Service;

/**
 * @author YUDI-Corgi
 * @description
 */
@Service
public class EchoService {

    public String echo() {
        System.out.println("访问该服务实例.");;
        return "Hello, nacos service discovery.";
    }

}
