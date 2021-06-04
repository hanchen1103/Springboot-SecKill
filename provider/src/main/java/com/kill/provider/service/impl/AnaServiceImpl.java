package com.kill.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.service.AnaService;
import com.kill.provider.mapper.AnaDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
@org.springframework.stereotype.Service
public class AnaServiceImpl implements AnaService {

    @Autowired
    AnaDAO anaDAO;


    @Override
    public List<Map<String, Object>> daily() {
        return anaDAO.daily();
    }

    @Override
    public List<Map<String, Object>> profileLocation() {
        return anaDAO.profileLocation();
    }

    @Override
    public List<Map<String, Object>> HotSearch() {
        return anaDAO.HotSearch();
    }

    @Override
    public List<Map<String, Object>> selectDateMoney() {
        return anaDAO.selectDateMoney();
    }

    @Override
    public int getLike() {
        return anaDAO.getLike();
    }

    @Override
    public int getNewProfile() {
        return anaDAO.getNewProfile();
    }

    @Override
    public int selectLoginNow() {
        return anaDAO.selectLoginNow();
    }

    @Override
    public Object selectAmountDaily() {
        return anaDAO.selectAmountDaily();
    }

    @Override
    public Object selectAmountYesterday() {
        return anaDAO.selectAmountYesterday();
    }

    @Override
    public Object selectAmountMonth() {
        return anaDAO.selectAmountMonth();
    }

    @Override
    public Object selectAmountSeason() {
        return anaDAO.selectAmountSeason();
    }

    @Override
    public Object selectAmountYear() {
        return anaDAO.selectAmountYear();
    }

    @Override
    public int selectNowDays() {
        return anaDAO.selectNowDays();
    }

    @Override
    public int selectYestDays() {
        return anaDAO.selectYestDays();
    }

    @Override
    public int selectMonth() {
        return anaDAO.selectMonth();
    }

    @Override
    public int selectLastMonth() {
        return anaDAO.selectLastMonth();
    }
}
