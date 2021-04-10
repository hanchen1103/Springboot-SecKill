package com.kill.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.Gas;
import com.kill.consumer.service.GasService;

import java.util.List;

@Service
public class GasServiceImpl implements GasService {

    @Reference
    com.kill.api.service.GasService gasService;


    @Override
    public List<Gas> selectByMonth(int userId) {
        return gasService.selectByMonth(userId);
    }

    @Override
    public List<Gas> seleccByYear(int userId) {
        return gasService.seleccByYear(userId);
    }

    @Override
    public List<Gas> selectBySeason(int userId) {
        return gasService.selectBySeason(userId);
    }

    @Override
    public Integer addGas(Gas g) {
        return gasService.addGas(g);
    }

    @Override
    public Gas selectById(int id) {
        return gasService.selectById(id);
    }
}
