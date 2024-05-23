package com.cloud.apollo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ForkJoinPool;

/**
 * @author YUDI-Corgi
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class DeferredController {

    /**
     * <a href="https://juejin.cn/post/7019148185553600542">Spring DeferredResult 异步请求</a>，
     * 可以通过此博客的使用示例和原理解释
     * @return DeferredResult
     */
    @GetMapping("/deferred")
    public DeferredResult<ResponseEntity<?>> deferred() {

        log.info("进入方法，开始执行...");

        // 指定 timeout 测试超时回调
        // DeferredResult<ResponseEntity<?>> output = new DeferredResult<>(5000L);
        DeferredResult<ResponseEntity<?>> output = new DeferredResult<>();

        // 设置超时回调
        output.onTimeout(() -> output.setErrorResult(
                ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("Request timeout occurred.")));

        // 设置完成回调
        output.onCompletion(() -> {
            log.info("执行完成回调----");
            // 经过测试并没有，因为当调用该方法时会立即处理结果并返回给客户端，所以这里的执行是异步，是在请求响应后的
            // output.setResult(ResponseEntity.ok("看看是否覆盖了 ok"));
        });

        // 设置错误回调，此处并没有生效，反而是被常规异常处理 @ExceptionHandler 截获了
        // 官方文档描述也是使用此方式，而没有提到该错误回调，至少我没找到，略略略...
        output.onError(ex -> {
            log.error("错误回调进入了这里2333");
        });

        ForkJoinPool.commonPool().submit(() -> {
            log.info("Processing in separate thread");
            try {
                int a = 10/0;
                log.info("Sleeping for 3 seconds");
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
                output.setErrorResult(e);
            }
            // output.setResult(ResponseEntity.ok("ok"));
        });

        log.info("比 ForkJoinPool 池中任务先执行...");
        return output;
    }

}
