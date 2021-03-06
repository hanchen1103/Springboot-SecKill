package com.kill.provider.service.impl;

import com.kill.api.model.Comment;
import com.kill.api.model.Message;
import com.kill.api.model.Profile;
import com.kill.api.model.Stock;
import com.kill.api.service.LikeService;
import com.kill.api.service.UserService;
import com.kill.provider.config.KafkaProducer;
import com.kill.provider.mapper.CommentDAO;
import com.kill.provider.mapper.ProfileDAO;
import com.kill.provider.mapper.StockDAO;
import com.kill.provider.util.JedisAdapter;
import com.kill.provider.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@com.alibaba.dubbo.config.annotation.Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    JedisAdapter jedisAdapter;

    @Autowired
    StockDAO stockDAO;

    @Autowired
    ProfileDAO profileDAO;

    @Autowired
    CommentDAO commentDAO;

    @Autowired
    KafkaProducer kafkaProducer;

    @Value("${systemId}")
    Integer systemId;

    @Override
    public Long getLikeCount(Integer entityType, Integer entityId) {
        if(entityId == null || entityType == null) {
            throw new NullPointerException();
        }
        String LikeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        return jedisAdapter.scard(LikeKey);
    }

    @Override
    public Long like(Integer userId, Integer entityType, Integer entityId) {
        if(userId == null || entityId == null || entityType == null) {
            throw new NullPointerException();
        }
        String LikeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.sadd(LikeKey, String.valueOf(userId));
        Message mes = new Message();
        mes.setFromId(systemId);
        mes.setCreateDate(new Date());
        Profile profile = profileDAO.selectByUserId(userId);
        if(entityType == 1) {
            Stock stock = stockDAO.selectById(entityId);
            int hotelId = stock.getUserId();
            mes.setToId(hotelId);
            mes.setContent("?????? " + profile.getNickName() + "?????????????????????: " + stock.getName());
        }
        else if (entityType == 2) {
            mes.setToId(entityId);
            mes.setContent("?????? " + profile.getNickName() + "????????????");
        }
        else if (entityType == 3) {
            Comment comment = commentDAO.selectById(entityId);
            int cid = comment.getUserId();
            mes.setToId(cid);
            mes.setContent("?????? " + profile.getNickName() + "???????????????????????????: " + comment.getContent());
        }
        else {
            mes.setToId(entityId);
            mes.setContent("?????? " + profile.getNickName() + "?????????????????????");
        }
        kafkaProducer.sendMessageLike(mes);
        return jedisAdapter.scard(LikeKey);
    }

    @Override
    public Long disLike(Integer userId, Integer entityType, Integer entityId) {
        if(userId == null || entityId == null || entityType == null) {
            throw new NullPointerException();
        }
        String LikeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.srem(LikeKey, String.valueOf(userId));
        return jedisAdapter.scard(LikeKey);
    }

    @Override
    public int getLikeStatus(Integer userId, Integer entityType, Integer entityId) {
        if(userId == null || entityId == null || entityType == null) {
            throw new NullPointerException();
        }
        String LikeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        if(jedisAdapter.sismember(LikeKey, String.valueOf(userId)))
            return 1;
        return 0;
    }
}
