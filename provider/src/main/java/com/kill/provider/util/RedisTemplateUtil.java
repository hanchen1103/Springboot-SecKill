package com.kill.provider.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RedisTemplateUtil {

    @Resource
    RedisTemplate<String, String> redis;

    public Object add(String key, Integer num) {
        return key == null ? null : redis.opsForValue().increment(key, num);
    }

    public String get(String key) {
        return key == null ? null : redis.opsForValue().get(key);
    }
}
