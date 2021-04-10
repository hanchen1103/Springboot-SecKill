package com.kill.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.power;
import com.kill.consumer.service.PowerService;

import java.util.List;

@Service
public class PowerServiceImpl implements PowerService {

    @Reference
    com.kill.api.service.PowerService powerService;


    @Override
    public List<power> selectByMonth(int userId) {
        return powerService.selectByMonth(userId);
    }

    @Override
    public List<power> seleccByYear(int userId) {
        return powerService.seleccByYear(userId);
    }

    @Override
    public List<power> selectBySeason(int userId) {
        return powerService.selectBySeason(userId);
    }

    @Override
    public Integer addpower(power p) {
        return powerService.addpower(p);
    }

    @Override
    public power selectById(int id) {
        return powerService.selectById(id);
    }
}
