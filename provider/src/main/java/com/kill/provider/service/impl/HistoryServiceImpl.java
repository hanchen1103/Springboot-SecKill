package com.kill.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.history;
import com.kill.api.service.HistoryService;
import com.kill.provider.mapper.HistoryDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
@org.springframework.stereotype.Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    HistoryDAO historyDAO;

    @Override
    public List<history> selectByUserId(int userId) {
        return historyDAO.selectAll(userId);
    }

    @Override
    public int addQuestion(history h) {
        return historyDAO.addHistory(h);
    }
}
