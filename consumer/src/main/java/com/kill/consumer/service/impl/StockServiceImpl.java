package com.kill.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.Stock;
import com.kill.api.service.StockService;
import com.kill.consumer.service.StockSer;

import java.util.List;

@Service
@org.springframework.stereotype.Service
public class StockServiceImpl implements StockSer {

    @Reference
    StockService stockService;

    @Override
    public int getStockCount(int id) {
        return stockService.getStockCount(id);
    }

    @Override
    public Stock getStockById(int id) {
        return stockService.getStockById(id);
    }

    @Override
    public int updateStockById(Stock stock) {
        return stockService.updateStockById(stock);
    }

    @Override
    public int updateStockByOptimisticLock(int addSale, Stock stock) {
        return stockService.updateStockByOptimisticLock(addSale, stock);
    }

    @Override
    public int addStock(Stock stock) {
        return stockService.addStock(stock);
    }

    @Override
    public List<Stock> selectHot() {
        return stockService.selectHot();
    }
}
