package com.kill.consumer.service;

import com.kill.api.model.Message;

import java.util.List;

public interface MessageService {

    int addMessage(Message message);

    List<Message> getMessageById(String messageId, int offset, int limit);

    List<Message> getMessageList(int userId, int offset, int limit);

    int hasReadCount(int userId, String messageId);

    int isRead(int toId, int fromId);

    Message selectLatest(int toId, int fromId);

    Message selectByMessageId(String messageId);

}
