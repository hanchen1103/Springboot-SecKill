package com.kill.consumer.service;

import com.kill.api.model.StockOrder;

import java.math.BigDecimal;
import java.util.List;

public interface StockOrderService {

    /**
     * 创建订单
     * @param stockId 库存id
     * @throws Exception 异常
     */
    int createOrderUseRedis(int addSale, int stockId, int userId, BigDecimal price) throws Exception;

    /**
     * 创建订单
     * @param addSale 增加的销量
     * @param stockId 卖出的商品id
     * @param userId 用户id
     * @param price 价格
     * @throws Exception redis和kafka异常
     */
    void createOrderUseRedisAndKafka(int addSale, int stockId, int userId, BigDecimal price) throws Exception;

    StockOrder selectOrderById(int orderId);

    List<StockOrder> selectByUserId(int userId, int start, int end);

}
