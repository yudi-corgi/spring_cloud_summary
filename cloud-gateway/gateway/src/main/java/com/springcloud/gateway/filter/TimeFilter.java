package com.springcloud.gateway.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义过滤器，计算请求使用时间
 * @author YUDI
 * @date 2020/6/15 17:30
 */
public class TimeFilter implements GatewayFilter, Ordered {

    private static final Log log = LogFactory.getLog(GatewayFilter.class);
    private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // PRE 方式过滤，请求执行前存放了开始时间
        exchange.getAttributes().put(REQUEST_TIME_BEGIN,System.currentTimeMillis());
        return chain.filter(exchange).then(
            // POST 方式过滤，计算了请求所消耗的时间
            Mono.fromRunnable(()->{
                Long startTime = exchange.getAttribute(REQUEST_TIME_BEGIN);
                if (startTime != null) {
                    log.info(exchange.getRequest().getURI().getRawPath()+":"+(System.currentTimeMillis()-startTime)+" ms");
                }
            })
        );
    }

    /**
     * 过滤器优先级，值越小越先执行
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
