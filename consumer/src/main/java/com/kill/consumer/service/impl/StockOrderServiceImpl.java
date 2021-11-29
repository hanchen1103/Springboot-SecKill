package com.kill.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.StockOrder;
import com.kill.api.service.OrderService;
import com.kill.consumer.service.StockOrderService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StockOrderServiceImpl implements StockOrderService {

    @Reference
    private OrderService orderService;

    @Override
    public int createOrderUseRedis(Integer addSale, Integer stockId, Integer userId, BigDecimal price) throws Exception {
        return orderService.createOrderUseRedis(addSale, stockId, userId, price);
    }

    @Override
    public void createOrderUseRedisAndKafka(Integer addSale, Integer stockId, Integer userId, BigDecimal price) throws Exception {
        orderService.createOrderUseRedisAndKafka(addSale, stockId, userId, price);
    }

    @Override
    public StockOrder selectOrderById(Integer orderId) {
        return orderService.selectOrderById(orderId);
    }

    @Override
    public List<StockOrder> selectByUserId(Integer userId, Integer start, Integer end) {
        return orderService.selectByUserId(userId, start, end);
    }


}
