package com.springcloud.clientribbon.interceptor;

import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.URI;

/**
 * 自定义 Ribbon 请求拦截器
 * @author YUDI
 * @date 2020/6/9 16:57
 */
public class EasyLoadBalancerInterceptor implements ClientHttpRequestInterceptor {

    private LoadBalancerClient loadBalancerClient;
    private LoadBalancerRequestFactory requestFactory;

    public EasyLoadBalancerInterceptor(LoadBalancerClient loadBalancerClient, LoadBalancerRequestFactory requestFactory) {
        this.loadBalancerClient = loadBalancerClient;
        this.requestFactory = requestFactory;
    }

    public EasyLoadBalancerInterceptor(LoadBalancerClient loadBalancerClient) {
        this(loadBalancerClient,new LoadBalancerRequestFactory(loadBalancerClient));
    }

    public EasyLoadBalancerInterceptor() {

    }

    /**
     *
     * @param httpRequest
     * @param bytes
     * @param clientHttpRequestExecution
     * @return
     * @throws IOException
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes,
                                        ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        final URI originUri = httpRequest.getURI();
        String serviceName = originUri.getHost();
        System.out.println("进入自定义的请求拦截器中："+serviceName);
        Assert.state(serviceName != null,"请求 URI 不包含有效的主机:"+serviceName);
        return this.loadBalancerClient.execute(serviceName,requestFactory.createRequest(httpRequest,bytes,clientHttpRequestExecution));
    }
}
