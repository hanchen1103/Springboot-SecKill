package com.kill.api.service;

import com.kill.api.model.User;

public interface UserService {

    User selectByName(String username);

    User selectById(Integer id);

    void updateHead_url(String head_url, Integer id) throws IllegalAccessException;
}
