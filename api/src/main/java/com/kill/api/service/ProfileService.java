package com.kill.api.service;

import com.kill.api.model.Profile;

import java.util.Date;
import java.util.List;

public interface ProfileService {

    void addProfile(Profile profile);

    Profile selectByUserId(int userId);

    List<Profile> selectAll(int start, int end);

    void updateProfile(String bio, String sex, String name, String nickName, String location,
                       Date birthDay, String telNum, String cardID, String job, int userId);

    void updateHead_url(String head_url, int userId);

    int count();

    void updateStatus(int userId);


    void cancelStatus(int userId);

}
