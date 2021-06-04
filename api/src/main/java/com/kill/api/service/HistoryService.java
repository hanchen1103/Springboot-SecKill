package com.kill.api.service;

import com.kill.api.model.history;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface HistoryService {

    List<history> selectByUserId(int userId);

    int addQuestion(history h);
}
