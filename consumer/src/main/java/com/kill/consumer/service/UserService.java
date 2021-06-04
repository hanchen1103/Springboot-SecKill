package com.kill.consumer.service;

import com.kill.api.model.User;

public interface UserService {

    User selectByName(String username);

    User selectById(int id);

    void updateHead_url(String head_url, int id);
}
