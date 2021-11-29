package com.kill.api.service;

import com.kill.api.model.Killact;

import java.util.List;
import java.util.Set;

public interface KillService {

    int killPost(Killact killact);

    List<Killact> getAllKillAct();

    Killact selectById(Integer id);

    int joinAct(Integer actId, Integer stockId);

    int getStockAct(Integer stockId);

    Set<String> getJoinStock(Integer actId);

}
