package com.kill.api.service;

import com.kill.api.model.history;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface HistoryService {

    List<history> selectByUserId(Integer userId);

    Integer addQuestion(history h);
}
