package com.kill.api.service;

import com.kill.api.model.Stock;

import java.util.List;

public interface StockService {

    /**
     * 获取剩余库存
     * @param id 库存id
     * @return 整数
     */
    int getStockCount(Integer id);

    /**
     *根据库存id 查询信息
     * @param id 库存id
     * @return stock
     */
    Stock getStockById(Integer id);

    /**
     *更新库存信息
     * @param stock 库存
     * @return 库存id
     */
    int updateStockById(Stock stock);

    /**
     * 通过乐观锁更新库存
     * @param stock 库存
     * @return 库存id
     */
    int updateStockByOptimisticLock(Integer addSale, Stock stock);

    /**
     * 上传库存商品
     * @param stock 库存
     * @return id
     */
    int addStock(Stock stock);

    //int updateStockByOptimisticAndKafka(int stockId);

    List<Stock> selectHot();
}
