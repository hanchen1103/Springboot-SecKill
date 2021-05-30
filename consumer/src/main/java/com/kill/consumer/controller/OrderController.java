package com.kill.consumer.controller;

import com.kill.api.model.Killact;
import com.kill.api.model.Stock;
import com.kill.consumer.service.impl.ActServiceImpl;
import com.kill.consumer.service.impl.StockOrderServiceImpl;
import com.kill.consumer.service.impl.StockServiceImpl;
import com.kill.consumer.util.jsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;


@RestController
@RequestMapping("/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private StockOrderServiceImpl stockOrderService;

    @Autowired
    private ActServiceImpl actService;

    @Autowired
    private StockServiceImpl stockService;


    /**
     *
     * @param map 包含stockId
     * @return id
     */
    @PostMapping(value = "", produces = {"application/json;charset=UTF-8"})
    public String createOrder(@RequestBody Map<String, Object> map) {
        int id = 0;
        try {
            int res = stockOrderService.createOrderUseRedis(Integer.parseInt(String.valueOf(map.get("addSale"))),
                    Integer.parseInt(String.valueOf(map.get("stockId"))), Integer.parseInt(String.valueOf(map.get("userId"))),
                    BigDecimal.valueOf(Double.parseDouble(String.valueOf(map.get("price")))));
            if(res == -1) return jsonUtil.getJSONString(1, "创建订单失败");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return jsonUtil.getJSONString(id);
    }

    /**
     * 使用kafka消息队列
     * @param stockId 购买商品id
     * @param addSale 购买数
     * @param price 购买价格
     * @param userId 购买者
     * @return json,id
     */
    @GetMapping(value = "/kafka/{stockId}", produces = {"application/json;charset=UTF-8"})
    public String createKafka(@PathVariable int stockId, int addSale, BigDecimal price, int userId) {
        logger.info("sid=[{}]", stockId);
        int id = 0;
        try {
            stockOrderService.createOrderUseRedisAndKafka(addSale, stockId, userId, price);
        } catch (Exception e) {
            logger.error("exception", e);
        }
        return String.valueOf(id);
    }

    @PostMapping(value = "/buy/{stockId}", produces = {"application/json;charset=UTF-8"})
    public String buy(@PathVariable int stockId, int addSale, int userId) {
        logger.info("sid=[{}]", stockId);
        int actId = actService.getStockAct(stockId);
        if(actId == -1) {
            Stock stock = stockService.getStockById(stockId);
            try {
                stockOrderService.createOrderUseRedisAndKafka(addSale, stockId, userId, stock.getPrice());
            } catch (Exception e) {
                logger.error("exception", e);
                return jsonUtil.getJSONString(999, e.getMessage());
            }
        } else {
            Killact killact = actService.selectById(actId);
            BigDecimal discount = killact.getDiscount();
            Stock stock = stockService.getStockById(stockId);
            try {
                stockOrderService.createOrderUseRedisAndKafka(addSale, stockId, userId, stock.getPrice().multiply(discount));
            } catch (Exception e) {
                logger.error("exception", e);
                return jsonUtil.getJSONString(999, e.getMessage());
            }
        }
        return jsonUtil.getJSONString(0);
    }

}
