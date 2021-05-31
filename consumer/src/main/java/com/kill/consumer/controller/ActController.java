package com.kill.consumer.controller;

import com.kill.api.model.Killact;
import com.kill.api.model.Stock;
import com.kill.consumer.service.impl.ActServiceImpl;
import com.kill.consumer.service.impl.ProfileServiceImpl;
import com.kill.consumer.service.impl.StockServiceImpl;
import com.kill.consumer.service.impl.UserServiceImpl;
import com.kill.consumer.util.jsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/act")
public class ActController {

    @Autowired
    ActServiceImpl actService;

    @Autowired
    StockServiceImpl stockService;

    @Autowired
    ProfileServiceImpl profileService;

    @PostMapping(value = "", produces = {"application/json;charset=UTF-8"})
    public String addact(@RequestBody Map<String, Object> map) throws ParseException {
        Killact killact = new Killact();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = sdf.parse((String) map.get("startTime"));
        Date endTime = sdf.parse((String) map.get("endTime"));
        killact.setDaycount((int) map.get("dayCount"));
        killact.setDescri((String) map.get("descri"));
        killact.setStartTime(startTime);
        killact.setEndTime(endTime);
        killact.setDiscount(BigDecimal.valueOf(Double.parseDouble(String.valueOf(map.get("discount")))));
        killact.setName((String) map.get("name"));
        killact.setUserId(Integer.parseInt(String.valueOf(map.get("userId"))));
        return jsonUtil.getJSONString(0, actService.killPost(killact));
    }

    @GetMapping(value = "", produces = {"application/json;charset=UTF-8"})
    public String selectOne(int id) {
        Map<String, Object> map = new HashMap<>();
        Killact killact = actService.selectById(id);
        if(killact == null) return jsonUtil.getJSONString(999, "act is null");
        map.put("act", killact);
        Set<String> list = actService.getJoinStock(id);
        List<Map<String, Object>> res = new LinkedList<>();
        for(String i : list) {
            Map<String, Object> t = new HashMap<>();
            if(stockService.getStockById(Integer.parseInt(i)) == null) continue;
            t.put("stock", stockService.getStockById(Integer.parseInt(i)));
            if(profileService.selectByUserId(stockService.getStockById(Integer.parseInt(i)).getUserId()) == null) continue;
            t.put("shop", profileService.selectByUserId(stockService.getStockById(Integer.parseInt(i)).getUserId()));
            res.add(t);
        }
        map.put("stockInfo", res);
        return jsonUtil.getJSONString(0,map);
    }

    @GetMapping(value = "/all", produces = {"application/json;charset=UTF-8"})
    public String selectAll() {
        return jsonUtil.getJSONString(0, actService.getAllKillAct());
    }

    @PostMapping(value = "/join", produces = {"application/json;charset=UTF-8"})
    public String join(int actId, int stockId) {
        return jsonUtil.getJSONString(actService.joinAct(actId, stockId));
    }
}
