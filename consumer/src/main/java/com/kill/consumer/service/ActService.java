package com.kill.consumer.service;

import com.kill.api.model.Killact;

import java.util.List;
import java.util.Set;

public interface ActService {

    int killPost(Killact killact);

    List<Killact> getAllKillAct();

    Killact selectById(int id);

    int joinAct(int actId, int stockId);

    int getStockAct(int stockId);

    Set<String> getJoinStock(int actId);

}
