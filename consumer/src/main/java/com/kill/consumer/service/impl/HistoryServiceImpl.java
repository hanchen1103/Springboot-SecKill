package com.kill.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.history;
import com.kill.consumer.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Reference
    com.kill.api.service.HistoryService historyService;

    @Override
    public List<history> selectByUserId(int userId) {
        return historyService.selectByUserId(userId);
    }

    @Override
    public int addQuestion(history h) {
        return historyService.addQuestion(h);
    }
}
