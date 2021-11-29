package com.kill.api.service;

import com.kill.api.model.Profile;

import java.util.Date;
import java.util.List;

public interface ProfileService {

    void addProfile(Profile profile);

    Profile selectByUserId(Integer userId);

    List<Profile> selectAll(Integer start, Integer end) throws IllegalAccessException;

    void updateProfile(String bio, String sex, String name, String nickName, String location,
                       Date birthDay, String telNum, String cardID, String job, int userId);

    void updateHead_url(String head_url, Integer userId) throws IllegalAccessException;

    Integer count();

    void updateStatus(Integer userId);


    void cancelStatus(Integer userId);

}
