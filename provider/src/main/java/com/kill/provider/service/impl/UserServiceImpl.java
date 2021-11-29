package com.kill.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.User;
import com.kill.api.service.UserService;
import com.kill.provider.mapper.UseDAO;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@org.springframework.stereotype.Service
public class UserServiceImpl implements UserService {

    @Autowired
    UseDAO useDAO;

    @Override
    public User selectByName(String username) {
        if(username == null || username.isEmpty()) {
            throw new NullPointerException("username can't be null");
        }
        return useDAO.selectByName(username);
    }

    @Override
    public User selectById(Integer id) {
        if(id == null) {
            throw new NullPointerException("id can't be null");
        }
        return useDAO.selectById(id);
    }

    @Override
    public void updateHead_url(String head_url, Integer id) throws IllegalAccessException {
        if(head_url == null || head_url.isEmpty() || id == null) {
            throw new NullPointerException();
        }
        if(selectById(id) == null) {
            throw new IllegalAccessException("user is null");
        }
        useDAO.updateHead_url(head_url, id);
    }
}
