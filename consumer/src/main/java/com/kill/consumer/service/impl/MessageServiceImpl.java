package com.kill.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.Message;
import com.kill.consumer.service.MessageService;

import java.util.List;

@Service
@org.springframework.stereotype.Service
public class MessageServiceImpl implements MessageService {

    @Reference
    com.kill.api.service.MessageService messageService;


    @Override
    public int addMessage(Message message) {
        return messageService.addMessage(message);
    }

    @Override
    public List<Message> getMessageById(String messageId, Integer offset, Integer limit) throws IllegalAccessException {
        return messageService.getMessageById(messageId, offset, limit);
    }

    @Override
    public List<Message> getMessageList(Integer userId, Integer offset, Integer limit) throws IllegalAccessException {
        return messageService.getMessageList(userId, offset, limit);
    }

    @Override
    public int hasReadCount(Integer userId, String messageId) {
        return messageService.hasReadCount(userId, messageId);
    }

    @Override
    public int isRead(Integer toId, Integer fromId) throws IllegalAccessException {
        return messageService.isRead(toId, fromId);
    }

    @Override
    public Message selectLatest(Integer toId, Integer fromId) {
        return messageService.selectLatest(toId, fromId);
    }

    @Override
    public Message selectByMessageId(String messageId) {
        return messageService.selectByMessageId(messageId);
    }

    @Override
    public List<Message> readComplaint(Integer start, Integer end) throws IllegalAccessException {
        return messageService.readComplaint(start, end);
    }

    @Override
    public Message selectById(Integer id) {
        return messageService.selectById(id);
    }

    @Override
    public int updateRead(Integer id) {
        return messageService.updateRead(id);
    }
}
