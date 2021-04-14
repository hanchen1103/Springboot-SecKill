package com.kill.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.spiderProduct;
import com.kill.api.service.CostService;
import com.kill.consumer.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Reference
    private CostService costService;

    @Override
    public Integer getCost(int a) {
        return costService.add(a);
    }

    @Override
    public List<spiderProduct> getSpider(String name) {
        return costService.spider(name);
    }
}
