package com.kill.provider.service.impl;

import com.kill.api.model.Stock;
import com.kill.api.model.StockOrder;
import com.kill.api.service.OrderService;
import com.kill.api.service.StockService;
import com.kill.provider.config.KafkaProducer;
import com.kill.provider.mapper.StockDAO;
import com.kill.provider.mapper.StockOrderDAO;
import com.kill.provider.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

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

    @Autowired
    KafkaProducer kafkaProducer;

    private int createOrder(Stock stock, Integer userId, BigDecimal price){
        if(stock == null || userId == null || price == null) {
            throw new NullPointerException();
        }
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
    public void saleStockByOptimistic(Integer addSale, Stock stock) throws IllegalAccessException {
        if(stock == null || addSale == null || addSale <= 0) {
            throw new IllegalAccessException("stock exception");
        }
        if(addSale + stock.getSale() > stock.getCount()) {
            throw new RuntimeException("Inventory shortage!");
        }
        int flag = stockService.updateStockByOptimisticLock(addSale, stock);
        if(flag == 0) {
            throw new RuntimeException("Failed to update inventory!");
        }
        redis.opsForValue().increment(RedisKeyUtil.STOCK_SALE + stock.getId(), addSale);
        redis.opsForValue().increment(RedisKeyUtil.STOCK_VERSION + stock.getId(), 1);
    }


    /**
     * 更新缓存
     * @param sid 商品id
     * @return stock
     */
    private Stock checkStockByRedis(Integer sid) {
        if(sid == null) {
            throw new NullPointerException("sid can't be null");
        }
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
    public int createOrderUseRedis(Integer addSale, Integer stockId, Integer userId, BigDecimal price) throws Exception {
        if(addSale == null || stockId == null || userId == null || price == null) {
            throw new NullPointerException();
        }
        Stock stock = checkStockByRedis(stockId);
        saleStockByOptimistic(addSale, stock);
        return createOrder(stock, userId, price);
    }

    @Override
    public void createOrderUseRedisAndKafka(Integer addSale, Integer stockId, Integer userId, BigDecimal price) throws Exception {
        if(addSale == null || stockId == null || userId == null || price == null) {
            throw new NullPointerException();
        }
        Stock stock = checkStockByRedis(stockId);
        stock.setPrice(price);
        stock.setUserId(userId);
        stock.setSale(addSale);
        kafkaProducer.sendOrderTopic(stock);
        logger.info("send kafka success");
    }

    @Override
    public StockOrder selectOrderById(Integer orderId) {
        if(orderId == null) {
            throw new NullPointerException("orderId can't be null");
        }
        return stockOrderDAO.selectById(orderId);
    }

    @Override
    public List<StockOrder> selectByUserId(Integer userId, Integer start, Integer end) {
        if(userId == null || start == null || end == null) {
            throw new NullPointerException("param can't be null");
        }
        if(start < 0 || end < 0) {
            throw new NullPointerException("param exception");
        }
        return stockOrderDAO.selectOrderUserId(userId, start, end);
    }

}
