package com.kill.provider.service.impl;

import com.kill.api.model.Killact;
import com.kill.api.service.KillService;
import com.kill.provider.mapper.KillDAO;
import com.kill.provider.util.JedisAdapter;
import com.kill.provider.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@com.alibaba.dubbo.config.annotation.Service
public class KillServiceImpl implements KillService {

    @Autowired
    KillDAO killDAO;

    @Autowired
    JedisAdapter jedisAdapter;

    @Override
    public int killPost(Killact killact) {
        if(killact == null) {
            throw new NullPointerException("killact can't be null");
        }
        int id = killDAO.addAct(killact);
        String ActKey = RedisKeyUtil.getActKey(id);
        jedisAdapter.sadd(ActKey, "0");
        jedisAdapter.expire(ActKey, killact.daycount * 24 * 60 * 60);
        return id;
    }

    @Override
    public List<Killact> getAllKillAct() {
        return killDAO.selectAll();
    }

    @Override
    public Killact selectById(Integer id) {
        if(id == null) {
            throw new NullPointerException("id can't be null");
        }
        return killDAO.selectById(id);
    }

    @Override
    public int joinAct(Integer actId, Integer stockId) {
        if(actId == null || stockId == null) {
            throw new NullPointerException("id can't be null");
        }
        String ActKey = RedisKeyUtil.getActKey(actId);
        String productKey = RedisKeyUtil.getREDIS_PRODUCT_ACT(stockId);
        jedisAdapter.sadd(ActKey, String.valueOf(stockId));
        int dayCount = selectById(actId).getDaycount();
        if(jedisAdapter.get(productKey) != null) return -1;
        jedisAdapter.setex(productKey, String.valueOf(actId), dayCount * 24 * 60 * 60);
        return 0;
    }

    @Override
    public int getStockAct(Integer stockId) {
        if(stockId == null) {
            throw new NullPointerException("stockId can't be null");
        }
        String productKey = RedisKeyUtil.getREDIS_PRODUCT_ACT(stockId);
        if (jedisAdapter.get(productKey) == null) return -1;
        return Integer.parseInt(jedisAdapter.get(productKey));
    }

    @Override
    public Set<String> getJoinStock(Integer actId) {
        if(actId == null) {
            throw new NullPointerException("actId can't be null");
        }
        String actKey = RedisKeyUtil.getActKey(actId);
        return jedisAdapter.smembers(actKey);
    }
}
