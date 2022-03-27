package com.springcloud.clientzuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 通过 Zuul 实现路由过滤器
 * @author YUDI
 * @date 2020/5/26 15:20
 */
@Component
public class ClientFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(ClientFilter.class);

    /**
     * 返回值代表过滤器类型
     * pre: 路由前
     * routing: 路由时
     * post: 路由后
     * error: 发送错误时调用
     * @return
     */
    @Override
    public String filterType() {
        return "route";
    }

    /**
     * 返回值代表过滤器顺序，过滤器可以多个，根据此值判断哪个先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 返回值表示是否要进行过滤操作，可写逻辑判断，此处直接为 true 全部过滤
     * @return
     */
    @Override
    public boolean shouldFilter() {
        /*
            这里是针对过滤器 run() 方法里是否设置了请求不进行路由，
            因为若是有后续过滤器，即使设置了不进行路由，也会继续过滤处理该请求
            通过这种方式可以判断请求若是被拒绝了，则过滤器也不需要过滤了
        */
        RequestContext ctx = RequestContext.getCurrentContext();
        if(!ctx.sendZuulResponse()){
            return false;
        }
        return true;
    }

    /**
     * 过滤器的具体执行逻辑，可判断权限，查询 SQL 等等
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        // 获取当前路由的请求上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HttpServletRequest request = ctx.getRequest();
        // 模拟获取参数 token，判断是否有权限调用服务
        Object token = request.getParameter("token");
        if(token == null){
            log.warn("Token is empty.");
            // 设置请求不进行路由转发，即不请求后端服务，但如果后续还有过滤器，则会继续被过滤器过滤处理
            ctx.setSendZuulResponse(false);
            // 响应码
            ctx.setResponseStatusCode(401);
            try{
                ctx.getResponse().getWriter().write("Token is empty.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        log.info("Routing is OK.");
        return null;
    }
}
