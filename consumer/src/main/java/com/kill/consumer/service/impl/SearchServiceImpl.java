package com.kill.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.Profile;
import com.kill.api.model.Stock;
import com.kill.api.service.SearchService;
import com.kill.consumer.service.SearchSercice;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchSercice {

    @Reference
    SearchService searchService;

    @Override
    public List<Stock> searchStock(String q) {
        return searchService.searchStock(q);
    }

    @Override
    public List<Profile> selectProfile(String q) {
        return searchService.selectProfile(q);
    }

}
