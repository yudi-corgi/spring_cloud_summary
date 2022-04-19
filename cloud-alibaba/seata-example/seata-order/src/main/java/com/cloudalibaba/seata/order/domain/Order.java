package com.cloudalibaba.seata.order.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author YUDI-Corgi
 * @description Order 实体，表名之所以加个 c_，是因为 order 为 MySQL 关键词
 */
@Data
@TableName("c_order")
public class Order {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String userId;
    private String commodityCode;
    private Integer count;
    private Integer money;

}
