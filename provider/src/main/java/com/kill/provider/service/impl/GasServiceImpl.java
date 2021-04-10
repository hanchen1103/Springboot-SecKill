package com.kill.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.Gas;
import com.kill.api.service.GasService;
import com.kill.provider.mapper.GasDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
@org.springframework.stereotype.Service
public class GasServiceImpl implements GasService {

    @Autowired
    GasDAO gasDAO;

    @Override
    public List<Gas> selectByMonth(int userId) {
        return gasDAO.selectByUserIdMonth(userId);
    }

    @Override
    public List<Gas> seleccByYear(int userId) {
        return gasDAO.selectByUserIdYear(userId);
    }

    @Override
    public List<Gas> selectBySeason(int userId) {
        return gasDAO.selectByUserIdSeason(userId);
    }

    @Override
    public Integer addGas(Gas g) {
        return gasDAO.addGas(g);
    }

    @Override
    public Gas selectById(int id) {
        return gasDAO.selectById(id);
    }
}
