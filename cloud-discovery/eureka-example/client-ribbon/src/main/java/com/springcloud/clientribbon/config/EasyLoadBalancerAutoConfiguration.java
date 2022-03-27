package com.springcloud.clientribbon.config;

import com.springcloud.clientribbon.interceptor.EasyLoadBalancerInterceptor;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 配置类，将自定义拦截器加载到 RestTemplate 中
 * @author YUDI
 * @date 2020/6/9 17:25
 */
@Configuration
public class EasyLoadBalancerAutoConfiguration {

    //@EasyLoadBalanced
    @Autowired(required = false)
    private List<RestTemplate> restTemplates = Collections.emptyList();

    @Bean
    public EasyLoadBalancerInterceptor easyLoadBalancerInterceptor(){
        return new EasyLoadBalancerInterceptor();
    }

    @Bean
    public SmartInitializingSingleton easyLoadBalancedRestTemplateInitializer(){
        return new SmartInitializingSingleton() {
            @Override
            public void afterSingletonsInstantiated() {
                for(RestTemplate restTemplate : EasyLoadBalancerAutoConfiguration.this.restTemplates){
                    List<ClientHttpRequestInterceptor> list = new ArrayList<>(restTemplate.getInterceptors());
                    list.add(easyLoadBalancerInterceptor());
                    restTemplate.setInterceptors(list);
                }
            }
        };
    }



}
