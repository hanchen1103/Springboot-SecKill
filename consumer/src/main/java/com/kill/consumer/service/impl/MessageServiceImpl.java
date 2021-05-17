package com.kill.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.Message;
import com.kill.consumer.service.MessageService;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Reference
    com.kill.api.service.MessageService messageService;


    @Override
    public int addMessage(Message message) {
        return messageService.addMessage(message);
    }

    @Override
    public List<Message> getMessageById(String messageId, int offset, int limit) {
        return messageService.getMessageById(messageId, offset, limit);
    }

    @Override
    public List<Message> getMessageList(int userId, int offset, int limit) {
        return messageService.getMessageList(userId, offset, limit);
    }

    @Override
    public int hasReadCount(int userId, String messageId) {
        return messageService.hasReadCount(userId, messageId);
    }

    @Override
    public int isRead(int toId, int fromId) {
        return messageService.isRead(toId, fromId);
    }

    @Override
    public Message selectLatest(int toId, int fromId) {
        return messageService.selectLatest(toId, fromId);
    }

    @Override
    public Message selectByMessageId(String messageId) {
        return messageService.selectByMessageId(messageId);
    }

    @Override
    public List<Message> readComplaint(int start, int end) {
        return messageService.readComplaint(start, end);
    }

    @Override
    public Message selectById(int id) {
        return messageService.selectById(id);
    }

    @Override
    public int updateRead(int id) {
        return messageService.updateRead(id);
    }
}
