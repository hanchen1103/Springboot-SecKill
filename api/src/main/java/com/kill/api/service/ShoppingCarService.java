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
     * 加商品从购物车中删除
     * @param id 用户id
     * @return 购物车数量
     */
    int delCar(int id);

    /**
     * 购物车数量统计
     * @param userId 用户id
     * @return 数量
     */
    int countCar(int userId);

    /**
     * 获取购物车详情
     * @param userId 用户id
     * @return stock的列表
     */
    List<ShopCar> getCar(int userId, int start, int offset);

}
