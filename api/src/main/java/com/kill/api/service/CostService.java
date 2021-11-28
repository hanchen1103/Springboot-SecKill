package com.kill.api.service;

import com.kill.api.model.spiderProduct;

import java.util.List;

public interface CostService {

    Integer add(int cost);

    List<spiderProduct> spider(String name);

    int delete();
}