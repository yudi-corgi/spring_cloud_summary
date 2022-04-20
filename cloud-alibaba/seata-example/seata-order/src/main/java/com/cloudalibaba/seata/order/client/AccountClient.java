package com.cloudalibaba.seata.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author YUDI-Corgi
 * @description Account Feign 接口
 */
@FeignClient("account-service")
public interface AccountClient {

    /**
     * 金额扣减
     * @param userId 用户ID
     * @param money 扣减金额
     */
    @PutMapping("/account/{userId}/{money}")
    void deduct(@PathVariable("userId") String userId, @PathVariable("money") Integer money);

    /**
     * 金额扣减（TCC）
     * @param userId 用户ID
     * @param money 扣减金额
     */
    @PutMapping("/account/tcc/{userId}/{money}")
    void tccDeduct(@PathVariable("userId") String userId, @PathVariable("money") Integer money);
}
