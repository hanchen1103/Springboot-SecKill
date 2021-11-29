package com.kill.api.service;

public interface LikeService {

    Long getLikeCount(Integer entityType, Integer entityId);

    Long like(Integer userId, Integer entityType, Integer entityId);

    Long disLike(Integer userId, Integer entityType, Integer entityId);

    int getLikeStatus(Integer userId, Integer entityType, Integer entityId);

}
