package com.kill.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.ShopCar;
import com.kill.api.service.ShoppingCarService;
import com.kill.provider.mapper.ShopCarDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
@org.springframework.stereotype.Service
public class ShoppingCarImpl implements ShoppingCarService {

    @Autowired
    ShopCarDAO shopCarDAO;

    public int addCar(ShopCar shopCar) {
        shopCarDAO.addCar(shopCar);
        return shopCar.getId();
    }

    @Override
    public int delCar(int userId) {
        return shopCarDAO.deleteShopCar(userId);
    }

    @Override
    public int countCar(int userId) {
        return shopCarDAO.getCount(userId);
    }

    @Override
    public List<ShopCar> getCar(int userId, int start, int offset) {
        return shopCarDAO.selectByUserId(userId, start,offset);
    }
}
