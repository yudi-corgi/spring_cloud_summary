package com.cloudalibaba.sentinel.provider.Service;

import com.cloudalibaba.sentinel.api.service.EchoService;
import org.springframework.stereotype.Service;

/**
 * @author YUDI-Corgi
 * @description
 */
@Service
public class EchoServiceImpl implements EchoService {

    @Override
    public String echo(int id) {
        if (id == 1) {
            // 模拟异常
            int a = 1/0;
        }
        return "Hello world!";
    }

    @Override
    public String draw() {
        return "The Last Supper!";
    }

}
