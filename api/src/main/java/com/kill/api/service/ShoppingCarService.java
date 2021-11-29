package com.kill.api.service;

import com.kill.api.model.ShopCar;

import java.util.List;

public interface ShoppingCarService {

    /**
     * 加入购物车
     * @return id
     */
    int addCar(ShopCar shopCar);

    /**
     * 商品从购物车中删除
     * @param id 用户id
     * @return 购物车数量
     */
    int delCar(Integer id) throws IllegalAccessException;

    /**
     * 购物车数量统计
     * @param userId 用户id
     * @return 数量
     */
    int countCar(Integer userId) throws IllegalAccessException;

    /**
     * 获取购物车详情
     * @param userId 用户id
     * @return stock的列表
     */
    List<ShopCar> getCar(Integer userId, Integer start, Integer offset) throws IllegalAccessException;

}
