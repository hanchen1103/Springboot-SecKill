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
public class LoginServiceImpl implements LoginService {

    @Autowired
    UseDAO userdao;


    @Autowired
    LoginTicketDAO loginticketdao;


    public void updateHead_url(String head_url, int id) {
        userdao.updateHead_url(head_url, id);
    }


    public void upddateLevel(int level, int id) {
        userdao.updateLevel(level, id);
    }



    @Override
    public Map<String, Object> register(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        if(username.isEmpty()) {
            map.put("msg", "用户名不能为空");
            return map;
        }

        if(password.isEmpty()) {
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = userdao.selectByName(username);
        if(user != null) {
            map.put("msg", "用户名已被注册");
            return map;
        }

        Random random = new Random();
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        String head = String.format("http://zhchaoshuai.%dt.png", random.nextInt(1000));
        user.setHead_url(head);
        user.setPassword(newProjectUtil.MD5(password + user.getSalt()));
        userdao.addUser(user);
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        map.put("id", user.getId());
        return map;
    }

    @Override
    public Map<String, Object> Login(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        if(username.isEmpty()) {
            map.put("msg", "用户名输入不可为空");
            return map;
        }

        if(password.isEmpty()) {
            map.put("msg", "密码输入不可为空");
            return map;
        }

        User user = userdao.selectByName(username);
        if(user == null) {
            map.put("msg", "用户不存在！");
            return map;
        }

        if(!newProjectUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "密码不正确！");
            return map;
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
        loginticketdao.updateStatus(ticket, 1);
    }

    public void updateUser(int id) {
        userdao.updateUser(id);
    }

    public void cancelUser(int id) {
        userdao.cancel(id);
    }

}
