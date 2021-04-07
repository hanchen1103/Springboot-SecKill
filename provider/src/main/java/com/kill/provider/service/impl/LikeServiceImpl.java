package com.kill.provider.service.impl;

import com.kill.api.service.LikeService;
import com.kill.provider.util.JedisAdapter;
import com.kill.provider.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@com.alibaba.dubbo.config.annotation.Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    JedisAdapter jedisAdapter;

    @Override
    public Long getLikeCount(int entityType, int entityId) {
        String LikeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        return jedisAdapter.scard(LikeKey);
    }

    @Override
    public Long like(int userId, int entityType, int entityId) {
        String LikeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.sadd(LikeKey, String.valueOf(userId));
        return jedisAdapter.scard(LikeKey);
    }

    @Override
    public Long disLike(int userId, int entityType, int entityId) {
        String LikeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.srem(LikeKey, String.valueOf(userId));
        return jedisAdapter.scard(LikeKey);
    }

    @Override
    public int getLikeStatus(int userId, int entityType, int entityId) {
        String LikeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        if(jedisAdapter.sismember(LikeKey, String.valueOf(userId)))
            return 1;
        return 0;
    }
}
