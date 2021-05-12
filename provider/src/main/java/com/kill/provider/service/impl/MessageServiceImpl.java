package com.kill.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.Message;
import com.kill.provider.mapper.MessageDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
@org.springframework.stereotype.Service
public class MessageServiceImpl {

    @Autowired
    MessageDAO messagedao;

    public int addMessage(Message message){
        return messagedao.addMessage(message) > 0 ? message.getId() : 0;
    }

    public List<Message> getMessageById(String messageId, int offset, int limit) {
        return messagedao.selectByMessageId(messageId, offset, limit);
    }

    public List<Message> getMessageList(int userId, int offset, int limit) {
        return messagedao.getMessageProfile(userId, offset, limit);
    }

    public int hasReadCount(int userId, String messageId) {
        return messagedao.getMessgaeCount(userId, messageId);
    }

    public int isRead(int toId, int fromId) {
        return messagedao.setRead(toId, fromId);
    }

    public Message selectLatest(int toId, int fromId) {
        return messagedao.selectLatest(toId, fromId);
    }

    public Message selectByMessageId(String messageId) {
        return messagedao.selectLatestById(messageId);
    }

}
