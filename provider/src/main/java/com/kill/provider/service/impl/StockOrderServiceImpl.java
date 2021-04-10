package com.kill.provider.service.impl;

import com.kill.api.model.Stock;
import com.kill.api.model.StockOrder;
import com.kill.api.service.OrderService;
import com.kill.api.service.StockService;
import com.kill.provider.mapper.StockDAO;
import com.kill.provider.mapper.StockOrderDAO;
import com.kill.provider.util.RedisKeyUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Transactional(rollbackFor = Exception.class)
@Service
@com.alibaba.dubbo.config.annotation.Service
public class StockOrderServiceImpl implements OrderService {

    private final Logger logger = LoggerFactory.getLogger(StockOrderServiceImpl.class);

    @Resource
    private StockService stockService;

    @Autowired
    StockOrderDAO stockOrderDAO;

    @Autowired
    RedisTemplate<String, String> redis;

    @Autowired
    StockDAO stockDAO;



    private int createOrder(Stock stock, int userId, BigDecimal price){
        StockOrder stockOrder = new StockOrder();
        stockOrder.setStockId(stock.getId());
        stockOrder.setUserId(userId);
        stockOrder.setOrder_uuid((String.valueOf(new Date().getTime()) + userId));
        stockOrder.setCreateDate(new Date());
        stockOrder.setPrice(price);
        return stockOrderDAO.addStockOrder(stockOrder);
    }


    /**
     * 乐观锁更新数据库和redis
     * @param stock 库存信息
     */
    private void saleStockByOptimistic(int addSale, Stock stock) {
        int flag = stockService.updateStockByOptimisticLock(addSale, stock);
        if(flag == 0) {
            throw new RuntimeException("更新库存失败");
        }
        redis.opsForValue().increment(RedisKeyUtil.STOCK_SALE + stock.getId(), 1);
        redis.opsForValue().increment(RedisKeyUtil.STOCK_VERSION + stock.getId(), 1);
    }

    private Stock checkStockByRedis(int sid) throws Exception {
        if(redis.opsForValue().get(RedisKeyUtil.STOCK_COUNT + sid) == null ||
                redis.opsForValue().get(RedisKeyUtil.STOCK_SALE + sid) == null
        || redis.opsForValue().get(RedisKeyUtil.STOCK_VERSION + sid) == null) {
            Stock stock = stockService.getStockById(sid);
            redis.opsForValue().set(RedisKeyUtil.STOCK_COUNT + sid, String.valueOf(stock.getCount()));
            redis.opsForValue().set(RedisKeyUtil.STOCK_SALE + sid, String.valueOf(stock.getSale()));
            redis.opsForValue().set(RedisKeyUtil.STOCK_VERSION + sid, String.valueOf(stock.getVersion()));
        }
        Integer count = Integer.parseInt(Objects.requireNonNull(redis.opsForValue().get(RedisKeyUtil.STOCK_COUNT + sid)));
        Integer sale = Integer.parseInt(Objects.requireNonNull(redis.opsForValue().get(RedisKeyUtil.STOCK_SALE + sid)));
        if(count.equals(sale)) {
            throw new RuntimeException("库存不足: " + sale);
        }
        Integer version = Integer.parseInt(Objects.requireNonNull(redis.opsForValue().get(RedisKeyUtil.STOCK_VERSION + sid)));
        Stock stock = new Stock();
        stock.setId(sid);
        stock.setCount(count);
        stock.setSale(sale);
        stock.setVersion(version);
        return stock;
    }

    @Override
    public int createOrderUseRedis(int addSale, int stockId, int userId, BigDecimal price) throws Exception {
        Stock stock = checkStockByRedis(stockId);
        saleStockByOptimistic(addSale, stock);
        return createOrder(stock, userId, price);
    }

    @Override
    public void createOrderUseRedisAndKafka(int stockId) throws Exception {
//        Stock stock = checkStockByRedis(stockId);
//        kafkaProducer.send(new ProducerRecord(kafkaTopic, stock));
//        logger.info("send kafka success");
    }

}
