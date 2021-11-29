package com.kill.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.ShopCar;
import com.kill.api.model.User;
import com.kill.api.service.ShoppingCarService;
import com.kill.provider.mapper.ShopCarDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
@org.springframework.stereotype.Service
public class ShoppingCarImpl implements ShoppingCarService {

    @Autowired
    ShopCarDAO shopCarDAO;

    @Autowired
    UserServiceImpl userService;

    public int addCar(ShopCar shopCar) {
        if(shopCar == null) {
            throw new NullPointerException("shopCar can't be null");
        }
        shopCarDAO.addCar(shopCar);
        return shopCar.getId();
    }

    @Override
    public int delCar(Integer userId) throws IllegalAccessException {
        User user = userService.selectById(userId);
        if(user == null || user.getStatus() != 0) {
            throw new IllegalAccessException("user account exception");
        }
        return shopCarDAO.deleteShopCar(userId);
    }

    @Override
    public int countCar(Integer userId) throws IllegalAccessException {
        User user = userService.selectById(userId);
        if(user == null || user.getStatus() != 0) {
            throw new IllegalAccessException("user account exception");
        }
        return shopCarDAO.getCount(userId);
    }

    @Override
    public List<ShopCar> getCar(Integer userId, Integer start, Integer offset) throws IllegalAccessException {
        if(userId == null || start == null || offset == null) {
            throw new NullPointerException("param can't be null");
        }
        if(start < 0 || offset < 0) {
            throw new IllegalAccessException("param exception");
        }
        return shopCarDAO.selectByUserId(userId, start,offset);
    }
}
