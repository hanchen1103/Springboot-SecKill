package com.kill.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.Profile;
import com.kill.api.service.ProfileService;
import com.kill.consumer.service.ProfileSer;

@Service
public class ProfileServiceImpl implements ProfileSer {

    @Reference
    ProfileService profileService;

    @Override
    public void addProfile(Profile profile) {
        profileService.addProfile(profile);
    }

    @Override
    public Profile selectByUserId(int userId) {
        return profileService.selectByUserId(userId);
    }
}
