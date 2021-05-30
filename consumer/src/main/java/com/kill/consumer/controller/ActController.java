package com.kill.consumer.controller;

import com.kill.api.model.Killact;
import com.kill.consumer.service.impl.ActServiceImpl;
import com.kill.consumer.util.jsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/act")
public class ActController {

    @Autowired
    ActServiceImpl actService;

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
        return jsonUtil.getJSONString(0, actService.selectById(id));
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
