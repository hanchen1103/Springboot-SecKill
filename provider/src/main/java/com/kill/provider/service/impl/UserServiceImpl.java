package com.kill.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.User;
import com.kill.api.service.UserService;
import com.kill.provider.mapper.UseDAO;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UseDAO useDAO;

    @Override
    public User selectByName(String username) {
        return useDAO.selectByName(username);
    }

    @Override
    public User selectById(int id) {
        return useDAO.selectById(id);
    }
}
