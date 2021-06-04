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
        profileDAO.addProfile(profile);
    }

    @Override
    public Profile selectByUserId(int userId) {
        return profileDAO.selectByUserId(userId);
    }

    @Override
    public List<Profile> selectAll(int start, int end) {
        return profileDAO.selectAll(start, end);
    }

    @Override
    public void updateProfile(String bio, String sex, String name, String nickName, String location, Date birthDay, String telNum, String cardID, String job, int userId) {
        profileDAO.updateProfile(bio, sex, name, nickName, location, birthDay, telNum, cardID, job, userId);
    }

    @Override
    public void updateHead_url(String head_url, int userId) {
        profileDAO.updateHead_url(head_url, userId);
    }

    @Override
    public int count() {
        return profileDAO.count();
    }

    @Override
    public void updateStatus(int userId) {
        profileDAO.updateStatus(userId);
    }

    @Override
    public void cancelStatus(int userId) {
        profileDAO.cancelStatus(userId);
    }
}
