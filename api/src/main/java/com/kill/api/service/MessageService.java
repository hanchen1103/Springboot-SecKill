package com.kill.api.service;

import com.kill.api.model.Message;

import java.util.List;

public interface MessageService {

    int addMessage(Message message);

    List<Message> getMessageById(String messageId, Integer offset, Integer limit) throws IllegalAccessException;

    List<Message> getMessageList(Integer userId, Integer offset, Integer limit) throws IllegalAccessException;

    int hasReadCount(Integer userId, String messageId);

    int isRead(Integer toId, Integer fromId) throws IllegalAccessException;

    Message selectLatest(Integer toId, Integer fromId);

    Message selectByMessageId(String messageId);

    List<Message> readComplaint(Integer start, Integer end) throws IllegalAccessException;

    Message selectById(Integer id);

    int updateRead(Integer id);

}
