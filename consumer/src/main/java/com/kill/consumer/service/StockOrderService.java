package com.kill.consumer.service;

import java.math.BigDecimal;

public interface StockOrderService {

    /**
     * 创建订单
     * @param stockId 库存id
     * @throws Exception 异常
     */
    void createOrderUseRedis(int addSale, int stockId, int userId, BigDecimal price) throws Exception;

    void createOrderUseRedisAndKafka(int stockId) throws Exception;

}
