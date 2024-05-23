package com.springcloud.zipkinclientone.filter;

import brave.Span;
import brave.Tracer;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TracingFilter 自定义过滤器
 * @author YUDI
 * @date 2020/6/10 22:09
 */
@Component
public class EasyFilter extends GenericFilterBean {

    private final Tracer tracer;

    public EasyFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        Span currentSpan = this.tracer.currentSpan();
        if (currentSpan == null) {
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        // 请求头中设置 TraceId
        ((HttpServletResponse)servletResponse).addHeader("ZIPKIN-TRACE-ID",currentSpan.context().traceIdString());
        // 设置标签，方便定位问题
        currentSpan.tag("easy-filter","tag");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
