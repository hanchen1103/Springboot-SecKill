package com.kill.provider.service.impl;

import com.kill.api.model.Message;
import com.kill.provider.config.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@com.alibaba.dubbo.config.annotation.Service
public class PowerServiceImpl implements PowerService {

    @Autowired
    PowerDAO powerDAO;

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Override
    public List<power> selectByMonth(int userId) {
        return powerDAO.selectByUserIdMonth(userId);
    }

    @Override
    public List<power> seleccByYear(int userId) {
        return powerDAO.selectByUserIdYear(userId);
    }

    @Override
    public List<power> selectBySeason(int userId) {
        return powerDAO.selectByUserIdSeason(userId);
    }

    @Override
    public Integer addpower(power p) {
        Message message = new Message();
        message.setContent("系统消息:您有一条新的电气资源信息已收集");
        message.setCreateDate(new Date());
        message.setFromId(9999099);
        message.setToId(p.getUserId());
        kafkaProducer.sendMessageResource(message);
        return powerDAO.addPower(p);
    }

    @Override
    public power selectById(int id) {
        return powerDAO.selectById(id);
    }
}
