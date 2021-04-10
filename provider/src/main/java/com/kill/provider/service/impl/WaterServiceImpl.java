package com.kill.provider.service.impl;

import com.kill.api.model.water;
import com.kill.api.service.WaterService;
import com.kill.provider.mapper.WaterDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@com.alibaba.dubbo.config.annotation.Service
public class WaterServiceImpl implements WaterService {

    @Autowired
    WaterDAO waterDAO;


    @Override
    public List<water> selectByMonth(int userId) {
        return waterDAO.selectByUserIdMonth(userId);
    }

    @Override
    public List<water> seleccByYear(int userId) {
        return waterDAO.selectByUserIdYear(userId);
    }

    @Override
    public List<water> selectBySeason(int userId) {
        return waterDAO.selectByUserIdSeason(userId);
    }

    @Override
    public Integer addWater(water w) {
        return waterDAO.addWater(w);
    }

    @Override
    public water selectById(int id) {
        return waterDAO.selectById(id);
    }
}
