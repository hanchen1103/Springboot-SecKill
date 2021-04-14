package com.kill.api.service;

import java.math.BigDecimal;

public interface OrderService {

    /**
     * 创建订单，乐观锁, 通过查询redis减小数据库压力
     * @param stockId 商品id
     * @return 0或1
     * @throws Exception 数据库错误
     */
    int createOrderUseRedis(int addSale, int stockId, int userId, BigDecimal price) throws Exception;

    /**
     *创建订单，乐观锁,库存查Redis减小数据库压力
     * @param stockId 商品id
     * @throws Exception kafka错误，redis异常
     */
    void createOrderUseRedisAndKafka(int addSale, int stockId, int userId, BigDecimal price) throws Exception;


}
