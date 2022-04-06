package com.cloudalibaba.sentinel.consumer.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 应用于 <strong>授权规则</strong> 的黑白名单拦截
 * @author YUDI-Corgi
 * @description 自定义请求来源解析器
 */
@ComponentScan
public class CustomRequestParser implements RequestOriginParser {

    @Override
    public String parseOrigin(HttpServletRequest req) {
        // 可根据业务场景及需求自定义请求来源
        // 比如请求必须是来自于网关，可为请求头添加 origin=gateway 进行标识
        // 然后在授权规则中调用方添加 gateway
        // origin!=gateway 的则进行拦截
        String origin = req.getHeader("origin");
        if (Objects.isNull(origin)) {
            origin = "other";
        }

        // 授权规则会判断配置的调用方是否包含了该返回结果，是则根据黑白名单类型放行或拦截
        return origin;
    }

}
