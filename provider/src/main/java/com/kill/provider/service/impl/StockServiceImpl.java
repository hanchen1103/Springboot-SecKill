package com.kill.provider.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.Stock;
import com.kill.api.service.StockService;
import com.kill.provider.mapper.StockDAO;

import javax.annotation.Resource;

@Service
public class StockServiceImpl implements StockService {

    @Resource
    StockDAO stockDAO;

    @Override
    public int getStockCount(int id) {
        Stock stock = stockDAO.selectById(id);
        return stock.getCount();
    }

    @Override
    public Stock getStockById(int id) {
        return stockDAO.selectById(id);
    }


    @Override
    public int updateStockById(Stock stock) {
        return stockDAO.updateStock(stock);
    }

    @Override
    public int updateStockByOptimisticLock(int sale, Stock stock) {
        return stockDAO.updateStockByOptimisticLock(sale, stock.getId(), stock.getVersion());
    }

    @Override
    public int addStock(Stock stock) {
        stockDAO.addStock(stock);
        return stock.getId();
    }


}
