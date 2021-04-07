package com.kill.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.Profile;
import com.kill.api.service.ProfileService;
import com.kill.provider.mapper.ProfileDAO;

import javax.annotation.Resource;

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
}
