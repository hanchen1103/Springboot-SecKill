package com.kill.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.water;
import com.kill.consumer.service.WaterService;

import java.util.List;

@Service
public class WaterServiceImpl implements WaterService {

    @Reference
    com.kill.api.service.WaterService waterService;

    @Override
    public List<water> selectByMonth(int userId) {
        return waterService.selectByMonth(userId);
    }

    @Override
    public List<water> seleccByYear(int userId) {
        return waterService.seleccByYear(userId);
    }

    @Override
    public List<water> selectBySeason(int userId) {
        return waterService.selectBySeason(userId);
    }

    @Override
    public Integer addWater(water w) {
        return waterService.addWater(w);
    }

    @Override
    public water selectById(int id) {
        return waterService.selectById(id);
    }
}
