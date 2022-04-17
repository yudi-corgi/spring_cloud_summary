package com.cloudalibaba.seata.storage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudalibaba.seata.storage.domain.Storage;

/**
 * @author YUDI-Corgi
 * @description Storage 服务接口
 */
public interface StorageService extends IService<Storage> {

    /**
     * 扣减库存
     * @param commodityCode 商品编码
     * @param count 商品数量
     */
    void deduct(String commodityCode, int count);

}
