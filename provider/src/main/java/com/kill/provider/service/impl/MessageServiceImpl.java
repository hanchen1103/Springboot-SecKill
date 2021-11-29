package com.kill.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.Message;
import com.kill.api.model.User;
import com.kill.api.service.MessageService;
import com.kill.provider.mapper.MessageDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
@org.springframework.stereotype.Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageDAO messagedao;

    @Autowired
    UserServiceImpl userService;

    @Override
    public int addMessage(Message message){
        if(message == null) {
            throw new NullPointerException("message can't be null");
        }
        return messagedao.addMessage(message) > 0 ? message.getId() : 0;
    }

    @Override
    public List<Message> getMessageById(String messageId, Integer offset, Integer limit) throws IllegalAccessException {
        if(messageId == null || offset == null || limit == null) {
            throw new NullPointerException("param can't be null");
        }
        if(offset < 0 || limit < 0) {
            throw new IllegalAccessException("param exception");
        }
        return messagedao.selectByMessageId(messageId, offset, limit);
    }

    @Override
    public List<Message> getMessageList(Integer userId, Integer offset, Integer limit) throws IllegalAccessException {
        if(userId == null || offset == null || limit == null) {
            throw new NullPointerException("param can't be null");
        }
        if(offset < 0 || limit < 0) {
            throw new IllegalAccessException("param exception");
        }
        return messagedao.getMessageProfile(userId, offset, limit);
    }

    @Override
    public int hasReadCount(Integer userId, String messageId) {
        if(userId == null || messageId == null) {
            throw new NullPointerException("userId and messageId can't be null");
        }
        return messagedao.getMessgaeCount(userId, messageId);
    }

    @Override
    public int isRead(Integer toId, Integer fromId) throws IllegalAccessException {
        if(toId == null || fromId == null) {
            throw new NullPointerException("toId and fromId can't be null");
        }
        User toUser = userService.selectById(toId);
        User fromUser = userService.selectById(fromId);
        if(toUser == null || fromUser == null || toUser.getStatus() != 0 || fromUser.getStatus() != 0) {
            throw new IllegalAccessException("user account exception ");
        }
        return messagedao.setRead(toId, fromId);
    }

    @Override
    public Message selectLatest(Integer toId, Integer fromId) {
        if(toId == null || fromId == null) {
            throw new NullPointerException("toId and fromId can't be null");
        }
        return messagedao.selectLatest(toId, fromId);
    }

    @Override
    public Message selectByMessageId(String messageId) {
        if(messageId == null) {
            throw new NullPointerException("id can't be null");
        }
        return messagedao.selectLatestById(messageId);
    }

    @Override
    public List<Message> readComplaint(Integer start, Integer end) throws IllegalAccessException {
        if(start == null || end == null) {
            throw new NullPointerException("param can't be null");
        }
        if(start < 0 || end < 0) {
            throw new IllegalAccessException("param exception");
        }
        return messagedao.selectComplaint(start, end);
    }

    @Override
    public Message selectById(Integer id) {
        if(id == null) {
            throw new NullPointerException("id can't be null");
        }
        return messagedao.selectById(id);
    }

    @Override
    public int updateRead(Integer id) {
        if(id == null) {
            throw new NullPointerException("id can't be null");
        }
        return messagedao.updateReadById(id);
    }


}
