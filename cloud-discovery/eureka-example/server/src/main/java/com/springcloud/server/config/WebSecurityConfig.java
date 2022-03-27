package com.springcloud.server.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author YUDI
 * @date 2020/6/8 17:07
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭 csrf 认证方式(Cross-site request forgery：跨站请求伪造)
        http.csrf().disable();
        super.configure(http);
    }
}
