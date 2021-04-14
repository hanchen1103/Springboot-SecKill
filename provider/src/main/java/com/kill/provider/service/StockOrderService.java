package com.kill.provider.service;

public interface StockOrderService {

    /**
     * 创建订单
     * @throws Exception 异常
     */
    int createOrderUseRedis(int stockId) throws Exception;

    void createOrderUseRedisAndKafka(int stockId) throws Exception;

}
