package com.cloudalibaba.seata.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudalibaba.seata.order.domain.Order;

/**
 * @author YUDI-Corgi
 * @description Order 服务接口
 */
public interface OrderService extends IService<Order> {

    /**
     * 创建订单
     * @param order 订单信息
     * @return 订单ID
     */
    Long create(Order order);

}
