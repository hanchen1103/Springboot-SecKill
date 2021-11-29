package com.kill.consumer.controller;

import com.kill.api.model.ShopCar;
import com.kill.consumer.service.impl.ShopCarServiceImpl;
import com.kill.consumer.service.impl.StockServiceImpl;
import com.kill.consumer.util.jsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@ComponentScan(value = "com.kill.consumer.config")
@RestController
@RequestMapping("shop-car")
public class ShoppingCarController {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCarController.class);

    @Autowired
    ShopCarServiceImpl shopCarService;

    @Autowired
    StockServiceImpl stockService;

    /**
     * 购物车功能
     * @param map 添加购物车
     * @return json code=0
     */
    @PostMapping(value = "", produces = {"application/json;charset=UTF-8"})
    public String addShop(@RequestBody Map<String, String> map) {
        int userId = Integer.parseInt(map.get("userId"));
        String size = map.get("size");
        int stockId = Integer.parseInt(map.get("stockId"));
        shopCarService.addCar(userId, stockId, size);
        return jsonUtil.getJSONString(0);
    }

    /**
     * 获取某人购物车
     * @param userId 某人id
     * @param start limit
     * @param limit offset
     * @return json包装的List<Map<String, Object>> 包含shopCar和stock
     */
    @GetMapping(value = "/{userId}/start={start}/limit={limit}", produces = {"application/json;charset=UTF-8"})
    public String selectCar(@PathVariable("userId") int userId,
                            @PathVariable("start") int start,
                            @PathVariable("limit") int limit) {
        try {
            List<ShopCar> list =  shopCarService.getCar(userId, start, limit);
            List<Map<String, Object>> res = new LinkedList<>();
            for(ShopCar shopCar : list) {
                Map<String, Object> t = new HashMap<>();
                t.put("shopCar", shopCar);
                t.put("stock", stockService.getStockById(shopCar.getStockId()));
                res.add(t);
            }
        } catch (IllegalAccessException | NumberFormatException e) {
            logger.error(e.getMessage());
        }
        return jsonUtil.getJSONString(500);
    }

    /**
     * 删除多项shop
     * @param map string
     * @return json
     */
    @DeleteMapping(value = "", produces = {"application/json;charset=UTF-8"})
    public String deleteShop(@RequestBody Map<String, String> map) {
        try {
            String str = map.get("shopCar");
            String[] list = str.split(",");
            for(String i : list) {
                shopCarService.delCar(Integer.parseInt(i));
            }
            return jsonUtil.getJSONString(0);
        } catch (IllegalAccessException | NumberFormatException e) {
            logger.error(e.getMessage());
        }
        return jsonUtil.getJSONString(500);
    }

}
