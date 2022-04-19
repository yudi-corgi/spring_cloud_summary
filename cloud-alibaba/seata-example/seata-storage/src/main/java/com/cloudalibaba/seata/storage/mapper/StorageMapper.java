package com.cloudalibaba.seata.storage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudalibaba.seata.storage.domain.Storage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author YUDI-Corgi
 * @description StorageMapper
 */
public interface StorageMapper extends BaseMapper<Storage> {

    /**
     * 扣减库存
     * @param commodityCode 商品编码
     * @param count 商品数量
     * @return 影响行数
     */
    @Update("update storage set `count` = `count` - #{count} where commodity_code = #{code}")
    int deduct(@Param("code") String commodityCode, @Param("count") int count);
}
