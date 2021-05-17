package com.kill.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.Profile;
import com.kill.api.model.Stock;
import com.kill.api.service.SearchService;
import com.kill.provider.mapper.SearchDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
@org.springframework.stereotype.Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    SearchDAO searchDAO;

    @Override
    public List<Stock> searchStock(String q) {
        return searchDAO.searchStock(q);
    }

    @Override
    public List<Profile> selectProfile(String q) {
        return searchDAO.selectProfile(q);
    }

}
