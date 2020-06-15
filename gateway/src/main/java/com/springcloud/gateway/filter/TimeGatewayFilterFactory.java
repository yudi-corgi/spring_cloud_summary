package com.springcloud.gateway.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * 自定义过滤器工厂
 * @author YUDI
 * @date 2020/6/15 18:11
 */
public class TimeGatewayFilterFactory extends AbstractGatewayFilterFactory<TimeGatewayFilterFactory.Config> {

    private static final Log log = LogFactory.getLog(GatewayFilter.class);
    private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";
    private static final String KEY = "withParams";

    // 重写该方法，设置自定义的过滤器参数
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(KEY);
    }

    // 通过构造器将静态内部类 Config 传递给父类 AbstractGatewayFilterFactory
    public TimeGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(TimeGatewayFilterFactory.Config config) {
        return (exchange, chain) -> {
            exchange.getAttributes().put(REQUEST_TIME_BEGIN, System.currentTimeMillis());
            return chain.filter(exchange).then(
                    Mono.fromRunnable(() -> {
                        Long startTime = exchange.getAttribute(REQUEST_TIME_BEGIN);
                        if (startTime != null) {
                            StringBuilder sb = new StringBuilder(exchange.getRequest().getURI().getRawPath())
                                    .append(": ")
                                    .append(System.currentTimeMillis() - startTime)
                                    .append("ms");
                            // 根据过滤器传递参数判断日志输出是否添加请求参数
                            if (config.isWithParams()) {
                                sb.append(" params:").append(exchange.getRequest().getQueryParams());
                            }
                            log.info(sb.toString());
                        }
                    })
            );
        };
    }

    // 静态内部类，该类用来调用过滤器的参数，可参考 RedirectToGatewayFilterFactory 类源码
    public static class Config {
        private boolean withParams;
        public boolean isWithParams() {
            return withParams;
        }
        public void setWithParams(boolean withParams) {
            this.withParams = withParams;
        }
    }


}
