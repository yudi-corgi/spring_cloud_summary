package com.cloudalibaba.seata.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author YUDI-Corgi
 * @description Storage Feign 接口
 */
@FeignClient("storage-service")
public interface StorageClient {

    /**
     * 库存扣减
     * @param code 商品编码
     * @param count 商品数量
     */
    @PutMapping("/storage/{code}/{count}")
    void deduct(@PathVariable("code") String code, @PathVariable("count") Integer count);
}
