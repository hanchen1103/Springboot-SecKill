package com.kill.api.service;

import java.util.Map;

public interface LoginService {

    Map<String, Object> Login(String username, String password) throws IllegalAccessException;

    Map<String, Object> register(String username, String password) throws IllegalAccessException;

    void layout(String ticket);
}
