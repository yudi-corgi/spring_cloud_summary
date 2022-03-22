package com.cloudalibaba.nacos.provider.service.impl;

import org.springframework.stereotype.Service;

/**
 * @author YUDI-Corgi
 * @description
 */
@Service
public class EchoService {

    public String echo() {
        return "Hello, nacos service discovery.";
    }

}
