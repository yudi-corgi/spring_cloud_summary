package com.cloudalibaba.sentinel.consumer.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author YUDI-Corgi
 * @description 自定义 BlockExceptionHandler
 */
@Component
public class CustomBlockExceptionHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, BlockException e) throws Exception {
        String msg = "未知异常";
        // 该状态码是 BlockException 默认状态码
        int status = 429;
        if (e instanceof FlowException) {
            msg = "请求被限流";
        } else if (e instanceof DegradeException) {
            msg = "请求被降级处理";
        } else if (e instanceof ParamFlowException) {
            msg = "热点参数限流";
        } else if (e instanceof AuthorityException) {
            msg = "请求没有权限";
            status = 401;
        }
        res.setContentType("application/json;charset=utf-8");
        res.setStatus(status);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", msg);
        jsonObject.put("status", status);
        res.getWriter().println(jsonObject.toJSONString());
    }
}
