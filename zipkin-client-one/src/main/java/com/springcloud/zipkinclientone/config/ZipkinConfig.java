package com.springcloud.zipkinclientone.config;

import brave.http.HttpRequest;
import brave.sampler.SamplerFunction;
import org.springframework.cloud.sleuth.instrument.web.HttpClientSampler;
import org.springframework.cloud.sleuth.instrument.web.SkipPatternProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.regex.Pattern;

/**
 * @author YUDI
 * @date 2020/6/10 22:26
 */
@Configuration
public class ZipkinConfig {

    /**
     * 通过实现 SamplerFunction<HttpRequest> 接口并注册为 Bean，对服务请求进行过滤
     * @param provider
     * @return
     */
    @Bean(name = HttpClientSampler.NAME)
    SamplerFunction<HttpRequest> EasyHttpSampler(SkipPatternProvider provider){
        // SkipPatternProvider.skipPattern 默认配置了许多过滤规则
        Pattern pattern = provider.skipPattern();
        return httpRequest -> {
            String url = httpRequest.path();
            boolean shouldSkip = pattern.matcher(url).matches();
            if(shouldSkip){
                return false;
            }
            return null;
        };
    }

}
