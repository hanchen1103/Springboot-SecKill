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

    /**
     * 选择指定用户的最近十条搜索历史
     * @param userId 用户id
     * @return list
     */
    @Override
    public List<history> selectByUserId(Integer userId) {
        if(userId == null) {
            throw new NullPointerException("userId can't be null");
        }
        return historyDAO.selectAll(userId);
    }

    /**
     * 添加搜索记录
     * @param h 记录
     * @return id
     */
    @Override
    public Integer addQuestion(history h) {
        if(h == null) {
            throw new NullPointerException("history can't be null");
        }
        return historyDAO.addHistory(h);
    }
}
