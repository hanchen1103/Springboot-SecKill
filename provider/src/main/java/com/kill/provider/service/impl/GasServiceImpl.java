package com.kill.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.Gas;
import com.kill.api.model.Message;
import com.kill.api.service.GasService;
import com.kill.provider.config.KafkaProducer;
import com.kill.provider.mapper.GasDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service
@org.springframework.stereotype.Service
public class GasServiceImpl implements GasService {

    @Autowired
    GasDAO gasDAO;

    @Autowired
    KafkaProducer kafkaProducer;

    @Override
    public List<Gas> selectByMonth(int userId) {
        return gasDAO.selectByUserIdMonth(userId);
    }

    @Override
    public List<Gas> seleccByYear(int userId) {
        return gasDAO.selectByUserIdYear(userId);
    }

    @Override
    public List<Gas> selectBySeason(int userId) {
        return gasDAO.selectByUserIdSeason(userId);
    }

    @Override
    public Integer addGas(Gas g) {
        Message message = new Message();
        message.setToId(g.getUserId());
        message.setFromId(9999099);
        message.setContent("系统消息:您有一条新的天然气资源信息已收集");
        message.setCreateDate(new Date());
        kafkaProducer.sendMessageResource(message);
        return gasDAO.addGas(g);
    }

    @Override
    public Gas selectById(int id) {
        return gasDAO.selectById(id);
    }
}
