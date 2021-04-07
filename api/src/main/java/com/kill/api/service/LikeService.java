package com.kill.api.service;

public interface LikeService {
    Long getLikeCount(int entityType, int entityId);

    Long like(int userId, int entityType, int entityId);

    Long disLike(int userId, int entityType, int entityId);

    int getLikeStatus(int userId, int entityType, int entityId);

}
