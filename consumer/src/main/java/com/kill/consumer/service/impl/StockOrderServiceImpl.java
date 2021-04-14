package com.kill.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.service.OrderService;
import com.kill.consumer.service.StockOrderService;

import java.math.BigDecimal;

@Service
public class StockOrderServiceImpl implements StockOrderService {

    @Reference
    private OrderService orderService;

    @Override
    public int createOrderUseRedis(int addSale, int stockId, int userId, BigDecimal price) throws Exception {
        return orderService.createOrderUseRedis(addSale, stockId, userId, price);
    }

    @Override
    public void createOrderUseRedisAndKafka(int addSale, int stockId, int userId, BigDecimal price) throws Exception {
        orderService.createOrderUseRedisAndKafka(addSale, stockId, userId, price);
    }


}
