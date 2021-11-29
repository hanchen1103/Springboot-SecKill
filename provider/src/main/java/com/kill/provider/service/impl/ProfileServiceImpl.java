package com.kill.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.Profile;
import com.kill.api.service.ProfileService;
import com.kill.provider.mapper.ProfileDAO;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Resource
    ProfileDAO profileDAO;


    @Override
    public void addProfile(Profile profile) {
        if(profile == null) {
            throw new NullPointerException("profile can't be null");
        }
        profileDAO.addProfile(profile);
    }

    @Override
    public Profile selectByUserId(Integer userId) {
        if(userId == null) {
            throw new NullPointerException("userId can't be null");
        }
        return profileDAO.selectByUserId(userId);
    }

    @Override
    public List<Profile> selectAll(Integer start, Integer end) throws IllegalAccessException {
        if(start < 0 || end < 0) {
            throw new IllegalAccessException("wrong `start` or `end` form");
        }
        return profileDAO.selectAll(start, end);
    }

    @Override
    public void updateProfile(String bio, String sex, String name, String nickName, String location, Date birthDay, String telNum, String cardID, String job, int userId) {
        profileDAO.updateProfile(bio, sex, name, nickName, location, birthDay, telNum, cardID, job, userId);
    }

    @Override
    public void updateHead_url(String head_url, Integer userId) throws IllegalAccessException {
        if(head_url == null || head_url.isEmpty() || userId == null) {
            throw new IllegalAccessException();
        }
        profileDAO.updateHead_url(head_url, userId);
    }

    @Override
    public Integer count() {
        return profileDAO.count();
    }

    @Override
    public void updateStatus(Integer userId) {
        if(userId == null) {
            throw new NullPointerException("userId can't be null");
        }
        profileDAO.updateStatus(userId);
    }

    @Override
    public void cancelStatus(Integer userId) {
        if(userId == null) {
            throw new NullPointerException("userId can't be null");
        }
        profileDAO.cancelStatus(userId);
    }
}
