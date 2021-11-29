package com.kill.provider.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.LoginTicket;
import com.kill.api.model.User;
import com.kill.api.service.LoginService;
import com.kill.provider.mapper.LoginTicketDAO;
import com.kill.provider.mapper.UseDAO;
import com.kill.provider.util.newProjectUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
@org.springframework.stereotype.Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UseDAO userdao;


    @Autowired
    LoginTicketDAO loginticketdao;


    @Override
    public Map<String, Object> register(String username, String password) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        if(username == null || username.isEmpty()) {
            throw new NullPointerException("username can't be empty");
        }

        if(password == null || password.isEmpty()) {
            throw new NullPointerException("password can't be empty");
        }

        User user = userdao.selectByName(username);
        if(user != null) {
            throw new IllegalAccessException("User has been registered");
        }

        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        String head = "null";
        user.setHead_url(head);
        user.setPassword(newProjectUtil.MD5(password + user.getSalt()));
        userdao.addUser(user);
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        map.put("id", user.getId());
        return map;
    }

    @Override
    public Map<String, Object> Login(String username, String password) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        if(username == null || username.isEmpty()) {
            throw new NullPointerException("username can't be empty");
        }

        if(password == null || password.isEmpty()) {
            throw new NullPointerException("password can't be empty");
        }

        User user = userdao.selectByName(username);
        if(user == null) {
            throw new IllegalAccessException("User does not exist");
        }

        if(!Objects.equals(newProjectUtil.MD5(password + user.getSalt()), user.getPassword())) {
            throw new IllegalAccessException("Incorrect password");
        }

        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }


    public String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 3600 * 24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginticketdao.addticket(ticket);
        return ticket.getTicket();
    }

    @Override
    public void layout(String ticket) {
        if(ticket == null) {
            throw new NullPointerException("ticket can't be null");
        }
        loginticketdao.updateStatus(ticket, 1);
    }

}
