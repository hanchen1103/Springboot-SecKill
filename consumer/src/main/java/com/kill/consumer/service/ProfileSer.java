package com.kill.consumer.service;

import com.kill.api.model.Profile;

public interface ProfileSer {

    void addProfile(Profile profile);

    Profile selectByUserId(int userId);
}
