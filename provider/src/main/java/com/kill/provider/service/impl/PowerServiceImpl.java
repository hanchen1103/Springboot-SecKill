package com.kill.provider.service.impl;

import com.kill.api.model.power;
import com.kill.api.service.PowerService;
import com.kill.provider.mapper.PowerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@com.alibaba.dubbo.config.annotation.Service
public class PowerServiceImpl implements PowerService {

    @Autowired
    PowerDAO powerDAO;

    @Override
    public List<power> selectByMonth(int userId) {
        return powerDAO.selectByUserIdMonth(userId);
    }

    @Override
    public List<power> seleccByYear(int userId) {
        return powerDAO.selectByUserIdYear(userId);
    }

    @Override
    public List<power> selectBySeason(int userId) {
        return powerDAO.selectByUserIdSeason(userId);
    }

    @Override
    public Integer addpower(power p) {
        return powerDAO.addPower(p);
    }

    @Override
    public power selectById(int id) {
        return powerDAO.selectById(id);
    }
}
