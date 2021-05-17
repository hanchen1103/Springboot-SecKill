package com.kill.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.Message;
import com.kill.api.service.MessageService;
import com.kill.provider.mapper.MessageDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
@org.springframework.stereotype.Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageDAO messagedao;

    @Override
    public int addMessage(Message message){
        return messagedao.addMessage(message) > 0 ? message.getId() : 0;
    }

    @Override
    public List<Message> getMessageById(String messageId, int offset, int limit) {
        return messagedao.selectByMessageId(messageId, offset, limit);
    }

    @Override
    public List<Message> getMessageList(int userId, int offset, int limit) {
        return messagedao.getMessageProfile(userId, offset, limit);
    }

    @Override
    public int hasReadCount(int userId, String messageId) {
        return messagedao.getMessgaeCount(userId, messageId);
    }

    @Override
    public int isRead(int toId, int fromId) {
        return messagedao.setRead(toId, fromId);
    }

    @Override
    public Message selectLatest(int toId, int fromId) {
        return messagedao.selectLatest(toId, fromId);
    }

    @Override
    public Message selectByMessageId(String messageId) {
        return messagedao.selectLatestById(messageId);
    }

    @Override
    public List<Message> readComplaint(int start, int end) {
        return messagedao.selectComplaint(start, end);
    }

    @Override
    public Message selectById(int id) {
        return messagedao.selectById(id);
    }

    @Override
    public int updateRead(int id) {
        return messagedao.updateReadById(id);
    }


}
