package com.kill.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.ShopCar;
import com.kill.consumer.service.ShoppingCarService;

import java.util.List;

@Service
@org.springframework.stereotype.Service
public class ShopCarServiceImpl implements ShoppingCarService {

    @Reference
    com.kill.api.service.ShoppingCarService shoppingCarService;

    @Override
    public int addCar(Integer userId, Integer stockId, String size) {
        ShopCar shopCar = new ShopCar();
        shopCar.setSize(size);
        shopCar.setUserId(userId);
        shopCar.setStockId(stockId);
        return shoppingCarService.addCar(shopCar);
    }

    @Override
    public int delCar(Integer id) throws IllegalAccessException {
        return shoppingCarService.delCar(id);
    }

    @Override
    public int countCar(Integer userId) throws IllegalAccessException {
        return shoppingCarService.countCar(userId);
    }

    @Override
    public List<ShopCar> getCar(Integer userId, Integer start, Integer offset) throws IllegalAccessException {
        return shoppingCarService.getCar(userId, start, offset);
    }
}
