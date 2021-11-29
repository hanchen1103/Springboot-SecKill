package com.kill.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.kill.consumer.service.LoginService;


import java.util.Map;

@Service
@org.springframework.stereotype.Service
public class LoginServiceImpl implements LoginService {

    @Reference
    com.kill.api.service.LoginService loginService;

    @Override
    public Map<String, Object> Login(String username, String password) throws IllegalAccessException {
        return loginService.Login(username, password);
    }

    @Override
    public Map<String, Object> register(String username, String password) throws IllegalAccessException {
        return loginService.register(username, password);
    }

    @Override
    public void layout(String ticket) {
        loginService.layout(ticket);
    }
}
