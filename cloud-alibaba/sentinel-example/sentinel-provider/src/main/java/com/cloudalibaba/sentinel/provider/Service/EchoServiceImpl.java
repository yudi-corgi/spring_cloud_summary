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
    public String echo() {
        return "Hello world!";
    }

}
