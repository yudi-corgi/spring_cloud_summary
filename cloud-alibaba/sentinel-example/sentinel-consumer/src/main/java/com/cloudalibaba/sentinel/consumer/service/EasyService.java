package com.cloudalibaba.sentinel.consumer.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

/**
 * @author YUDI-Corgi
 * @description
 */
@Service
public class EasyService {


    @SentinelResource("SubLaugh")
    public String subLaugh() {
        return "LOL...";
    }

    @SentinelResource("Sub2")
    public String subLaugh2() {
        return "LOL2...";
    }
}
