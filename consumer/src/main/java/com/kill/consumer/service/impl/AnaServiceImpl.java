package com.kill.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.kill.consumer.service.AnaService;

import java.util.List;
import java.util.Map;

@Service
@org.springframework.stereotype.Service
public class AnaServiceImpl implements AnaService {

    @Reference
    com.kill.api.service.AnaService anaService;

    @Override
    public List<Map<String, Object>> daily() {
        return anaService.daily();
    }

    @Override
    public List<Map<String, Object>> profileLocation() {
        return anaService.profileLocation();
    }

    @Override
    public List<Map<String, Object>> HotSearch() {
        return anaService.HotSearch();
    }

    @Override
    public List<Map<String, Object>> selectDateMoney() {
        return anaService.selectDateMoney();
    }

    @Override
    public int getLike() {
        return anaService.getLike();
    }

    @Override
    public int getNewProfile() {
        return anaService.getNewProfile();
    }

    @Override
    public int selectLoginNow() {
        return anaService.selectLoginNow();
    }

    @Override
    public Object selectAmountDaily() {
        return anaService.selectAmountDaily();
    }

    @Override
    public Object selectAmountYesterday() {
        return anaService.selectAmountYesterday();
    }

    @Override
    public Object selectAmountMonth() {
        return anaService.selectAmountMonth();
    }

    @Override
    public Object selectAmountSeason() {
        return anaService.selectAmountSeason();
    }

    @Override
    public Object selectAmountYear() {
        return anaService.selectAmountYear();
    }

    @Override
    public int selectNowDays() {
        return anaService.selectNowDays();
    }

    @Override
    public int selectYestDays() {
        return anaService.selectYestDays();
    }

    @Override
    public int selectMonth() {
        return anaService.selectMonth();
    }

    @Override
    public int selectLastMonth() {
        return anaService.selectLastMonth();
    }
}
