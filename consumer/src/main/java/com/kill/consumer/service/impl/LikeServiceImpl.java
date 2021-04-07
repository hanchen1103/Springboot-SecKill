package com.kill.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.kill.consumer.service.LikeService;

@Service
public class LikeServiceImpl implements LikeService {

    @Reference
    com.kill.api.service.LikeService likeService;

    @Override
    public Long getLikeCount(int entityType, int entityId) {
        return likeService.getLikeCount(entityType, entityId);
    }

    @Override
    public Long like(int userId, int entityType, int entityId) {
        return likeService.like(userId, entityType, entityId);
    }

    @Override
    public Long disLike(int userId, int entityType, int entityId) {
        return likeService.disLike(userId, entityType, entityId);
    }

    @Override
    public int getLikeStatus(int userId, int entityType, int entityId) {
        return likeService.getLikeStatus(userId, entityType, entityId);
    }

}
