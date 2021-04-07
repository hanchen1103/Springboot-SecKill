package com.kill.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.service.CostService;

@Service
public class CostServiceImpl implements CostService {

    private final Integer totalCost = 1000;

    @Override
    public Integer add(int cost) {
        return totalCost + cost;
    }
}
