package com.kill.consumer.service;

import java.util.Map;

public interface LoginService {

    Map<String, Object> Login(String username, String password);

    Map<String, Object> register(String username, String password);

    void layout(String ticket);
}
