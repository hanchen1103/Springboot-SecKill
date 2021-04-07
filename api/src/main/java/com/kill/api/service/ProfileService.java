package com.kill.api.service;

import com.kill.api.model.Profile;

public interface ProfileService {

    void addProfile(Profile profile);

    Profile selectByUserId(int userId);
}
