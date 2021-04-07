package com.kill.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.User;
import com.kill.consumer.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Reference
    com.kill.api.service.UserService userService;

    @Override
    public User selectByName(String username) {
        return userService.selectByName(username);
    }

    @Override
    public User selectById(int id) {
        return userService.selectById(id);
    }
}
