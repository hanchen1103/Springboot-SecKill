package com.kill.provider.service.impl;

import com.kill.api.model.Message;
import com.kill.api.model.water;
import com.kill.api.service.WaterService;
import com.kill.provider.config.KafkaProducer;
import com.kill.provider.mapper.WaterDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@com.alibaba.dubbo.config.annotation.Service
public class WaterServiceImpl implements WaterService {

    @Autowired
    WaterDAO waterDAO;

    @Autowired
    KafkaProducer kafkaProducer;

    @Override
    public List<water> selectByMonth(int userId) {
        return waterDAO.selectByUserIdMonth(userId);
    }

    @Override
    public List<water> seleccByYear(int userId) {
        return waterDAO.selectByUserIdYear(userId);
    }

    @Override
    public List<water> selectBySeason(int userId) {
        return waterDAO.selectByUserIdSeason(userId);
    }

    @Override
    public Integer addWater(water w) {
        Message message = new Message();
        message.setCreateDate(new Date());
        message.setFromId(9999099);
        message.setToId(w.getUserId());
        message.setContent("系统消息:您有一条新的水资源信息已收集");
        kafkaProducer.sendMessageResource(message);
        return waterDAO.addWater(w);
    }

    @Override
    public water selectById(int id) {
        return waterDAO.selectById(id);
    }
}
