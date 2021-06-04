package com.kill.consumer.service;

import com.kill.api.model.history;

import java.util.List;

public interface HistoryService {

    List<history> selectByUserId(int userId);

    int addQuestion(history h);
}
