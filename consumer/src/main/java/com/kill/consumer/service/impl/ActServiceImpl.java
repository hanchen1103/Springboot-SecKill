package com.kill.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.Killact;
import com.kill.api.service.KillService;
import com.kill.consumer.service.ActService;

import java.util.List;

@Service
public class ActServiceImpl implements ActService {

    @Reference
    KillService killService;

    @Override
    public int killPost(Killact killact) {
        return killService.killPost(killact);
    }

    @Override
    public List<Killact> getAllKillAct() {
        return killService.getAllKillAct();
    }

    @Override
    public Killact selectById(int id) {
        return killService.selectById(id);
    }

    @Override
    public int joinAct(int actId, int stockId) {
        return killService.joinAct(actId, stockId);
    }

    @Override
    public int getStockAct(int stockId) {
        return killService.getStockAct(stockId);
    }
}
