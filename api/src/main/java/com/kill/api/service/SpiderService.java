package com.kill.api.service;

import com.kill.api.model.spiderProduct;

import java.util.List;

public interface SpiderService {

    List<spiderProduct> getSpider(String name);
}
