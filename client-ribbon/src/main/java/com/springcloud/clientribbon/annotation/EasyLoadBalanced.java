package com.springcloud.clientribbon.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

/**
 * 自定义注解
 * @author YUDI
 * @date 2020/6/9 17:08
 */
@Target({ElementType.FIELD,ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Qualifier
public @interface EasyLoadBalanced {
}
