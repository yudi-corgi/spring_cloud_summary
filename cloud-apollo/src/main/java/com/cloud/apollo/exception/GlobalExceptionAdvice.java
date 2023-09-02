package com.cloud.apollo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author YUDI-Corgi
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public void exception(Exception e){
        log.error("错误回调进入这里: {}" + e.getMessage());
    }
}
