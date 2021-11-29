package com.kill.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.Profile;
import com.kill.api.service.ProfileService;
import com.kill.consumer.service.ProfileSer;

import java.util.Date;
import java.util.List;

@Service
@org.springframework.stereotype.Service
public class ProfileServiceImpl implements ProfileSer {

    @Reference
    ProfileService profileService;

    @Override
    public void addProfile(Profile profile) {
        profileService.addProfile(profile);
    }

    @Override
    public Profile selectByUserId(Integer userId) {
        return profileService.selectByUserId(userId);
    }

    @Override
    public List<Profile> selectAll(Integer start, Integer end) throws IllegalAccessException {
        return profileService.selectAll(start, end);
    }

    @Override
    public void updateProfile(String bio, String sex, String name, String nickName, String location, Date birthDay, String telNum, String cardID, String job, int userId) {
        profileService.updateProfile(bio, sex, name, nickName, location, birthDay, telNum, cardID, job, userId);
    }

    @Override
    public void updateHead_url(String head_url, Integer userId) throws IllegalAccessException {
        profileService.updateHead_url(head_url, userId);
    }

    @Override
    public int count() {
        return profileService.count();
    }

    @Override
    public void updateStatus(Integer userId) {
        profileService.updateStatus(userId);
    }

    @Override
    public void cancelStatus(Integer userId) {
        profileService.cancelStatus(userId);
    }
}
