package com.kill.api.service;

import com.kill.api.model.StockOrder;
import org.springframework.core.annotation.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    /**
     * 创建订单，乐观锁, 通过查询redis减小数据库压力
     * @param stockId 商品id
     * @return 0或1
     * @throws Exception 数据库错误
     */
    int createOrderUseRedis(Integer addSale, Integer stockId, Integer userId, BigDecimal price) throws Exception;

    /**
     *创建订单，乐观锁,库存查Redis减小数据库压力
     * @param stockId 商品id
     * @throws Exception kafka错误，redis异常
     */
    void createOrderUseRedisAndKafka(Integer addSale, Integer stockId, Integer userId, BigDecimal price) throws Exception;


    StockOrder selectOrderById(Integer orderId);


    List<StockOrder> selectByUserId(Integer userId, Integer start, Integer end);
}
