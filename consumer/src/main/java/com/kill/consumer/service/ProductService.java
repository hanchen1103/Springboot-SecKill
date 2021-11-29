package com.kill.consumer.service;

import com.kill.api.model.spiderProduct;

import java.util.List;

public interface ProductService {
    Integer getCost(Integer a);

    List<spiderProduct> getSpider(String name);

    int delete();
}
