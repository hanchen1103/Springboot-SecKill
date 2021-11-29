package com.kill.provider.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.EntityType;
import com.kill.api.model.Message;
import com.kill.api.model.Stock;
import com.kill.api.service.StockService;
import com.kill.api.service.UserService;
import com.kill.provider.config.KafkaProducer;
import com.kill.provider.mapper.StockDAO;
import com.kill.provider.mapper.UseDAO;
import com.kill.provider.util.JedisAdapter;
import com.kill.provider.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
@org.springframework.stereotype.Service
public class StockServiceImpl implements StockService {

    @Resource
    StockDAO stockDAO;

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    UseDAO useDAO;

    @Autowired
    JedisAdapter jedisAdapter;

    public Set<String> getCollectUser(Integer hotelId) {
        if(hotelId == null) {
            throw new NullPointerException("hotelId can't be null");
        }
        String LikeKey = RedisKeyUtil.getLikeKey(EntityType.ENTITYTYPE_HOTEL, hotelId);
        return jedisAdapter.smembers(LikeKey);
    }

    @Override
    public int getStockCount(Integer id) {
        if(id == null) {
            throw new NullPointerException("id can't be null");
        }
        Stock stock = stockDAO.selectById(id);
        return stock.getCount();
    }

    @Override
    public Stock getStockById(Integer id) {
        if(id == null) {
            throw new NullPointerException("id can't be null");
        }
        return stockDAO.selectById(id);
    }


    @Override
    public int updateStockById(Stock stock) {
        if(stock == null) {
            throw new NullPointerException("stock can't be null");
        }
        return stockDAO.updateStock(stock);
    }

    @Override
    public int updateStockByOptimisticLock(Integer sale, Stock stock) {
        if(stock == null || sale == null) {
            throw new NullPointerException("stock and sale can't be null");
        }
        return stockDAO.updateStockByOptimisticLock(sale, stock.getId(), stock.getVersion());
    }

    @Override
    public int addStock(Stock stock) {
        if(stock == null) {
            throw new NullPointerException("stock can't be null");
        }
        stockDAO.addStock(stock);
        Set<String> list = getCollectUser(stock.getUserId());
        if (list.size() != 0) {
            for(String i : list) {
                int id = Integer.parseInt(i);
                Message message = new Message();
                message.setContent("您关注的店铺" + useDAO.selectById(stock.getUserId()).getName()
                + "上新了 " + stock.getName() + ", 快来看看吧");
                message.setFromId(stock.getUserId());
                message.setCreateDate(new Date());
                message.setToId(id);
                kafkaProducer.sendMessageCollect(message);
            }
        }
        return stock.getId();
    }

    @Override
    public List<Stock> selectHot() {
        return stockDAO.selectHot();
    }


}
