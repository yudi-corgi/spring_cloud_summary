package com.cloudalibaba.seata.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudalibaba.seata.order.client.AccountClient;
import com.cloudalibaba.seata.order.client.StorageClient;
import com.cloudalibaba.seata.order.domain.Order;
import com.cloudalibaba.seata.order.mapper.OrderMapper;
import com.cloudalibaba.seata.order.service.OrderService;
import feign.FeignException;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author YUDI-Corgi
 * @description OrderService 实现类
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private OrderMapper orderMapper;
    private final AccountClient accountClient;
    private final StorageClient storageClient;

    public OrderServiceImpl(AccountClient accountClient, StorageClient storageClient) {
        this.accountClient = accountClient;
        this.storageClient = storageClient;
    }

    @Override
    @GlobalTransactional(name = "XA-CreateOrder")
    public Long create(Order order) {
        // 创建订单
        orderMapper.insert(order);
        try {
            // 扣用户余额
            accountClient.deduct(order.getUserId(), order.getMoney());
            // 扣库存
            storageClient.deduct(order.getCommodityCode(), order.getCount());
        } catch (FeignException e) {
            log.error("下单失败，原因:{}", e.contentUTF8(), e);
            throw new RuntimeException(e.contentUTF8(), e);
        }
        return order.getId();
    }

    @Override
    @GlobalTransactional(name = "XA-TccCreateOrder")
    public Long tccCreate(Order order) {
        // 创建订单
        orderMapper.insert(order);
        try {
            // 扣用户余额
            accountClient.tccDeduct(order.getUserId(), order.getMoney());
            // 扣库存
            storageClient.deduct(order.getCommodityCode(), order.getCount());
        } catch (FeignException e) {
            log.error("下单失败，原因:{}", e.contentUTF8(), e);
            throw new RuntimeException(e.contentUTF8(), e);
        }
        return order.getId();
    }

}
