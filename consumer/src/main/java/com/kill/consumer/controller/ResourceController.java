package com.kill.consumer.controller;

import com.crossoverjie.distributed.annotation.CommonLimit;
import com.kill.api.model.Gas;
import com.kill.api.model.power;
import com.kill.api.model.water;
import com.kill.consumer.service.GasService;
import com.kill.consumer.service.PowerService;
import com.kill.consumer.service.WaterService;
import com.kill.consumer.service.impl.GasServiceImpl;
import com.kill.consumer.service.impl.PowerServiceImpl;
import com.kill.consumer.service.impl.WaterServiceImpl;
import com.kill.consumer.util.jsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@ComponentScan(value = "com.kill.consumer.config")
@ComponentScan(value = "com.crossoverjie.distributed.intercept")
@RestController
public class ResourceController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    WaterServiceImpl waterService;

    @Autowired
    GasServiceImpl gasService;

    @Autowired
    PowerServiceImpl powerService;

    @PostMapping(value = "/water", produces = {"application/json;charset=UTF-8"})
    public String loadWater(@RequestBody Map<String, Object> map) {
        water w = new water();
        w.setAccuracy((Double) map.get("accuracy"));
        w.setCreateDate(new Date());
        w.setUserId((Integer) map.get("userId"));
        w.setFlow((Double) map.get("flow"));
        w.setElectric_frequenct((Double) map.get("electrie_frequenct"));
        Integer res = waterService.addWater(w);
        return jsonUtil.getJSONString(res);
    }

    @CommonLimit
    @PostMapping(value = "/gas", produces = {"application/json;charset=UTF-8"})
    public String loadGAS(@RequestBody Map<String, Object> map) {
        Gas w = new Gas();
        w.setAccuracy((Double) map.get("accuracy"));
        w.setCreateDate(new Date());
        w.setUserId((Integer) map.get("userId"));
        w.setFlow((Double) map.get("flow"));
        w.setHumidity((Double) map.get("humidity"));
        w.setTemperature((Double) map.get("temperature"));
        Integer res = gasService.addGas(w);
        return jsonUtil.getJSONString(res);
    }

    @PostMapping(value = "/power", produces = {"application/json;charset=UTF-8"})
    public String loadPower(@RequestBody Map<String, Object> map) {
        power w = new power();
        w.setElectric_current((Double) map.get("electric_current"));
        w.setCreateDate(new Date());
        w.setUserId((Integer) map.get("userId"));
        w.setFlow((Double) map.get("flow"));
        w.setVoltage((Double) map.get("voltage"));
        w.setHumidity((Double) map.get("humidity"));
        w.setTemperature((Double) map.get("temperature"));
        Integer res = powerService.addpower(w);
        return jsonUtil.getJSONString(res);
    }

    @CommonLimit
    @GetMapping(value = "/resource/month/", produces = {"application/json;charset=UTF-8"})
    public String getResouceByMonth(int type, int userId) {
        if(type == 1) return jsonUtil.getJSONString(0, waterService.selectByMonth(userId));
        if(type == 2) return jsonUtil.getJSONString(0, powerService.selectByMonth(userId));
        return jsonUtil.getJSONString(0, gasService.selectByMonth(userId));
    }

    @CommonLimit
    @GetMapping(value = "/resource/season/", produces = {"application/json;charset=UTF-8"})
    public String getResouceBySeason(int type, int userId) {
        if(type == 1) return jsonUtil.getJSONString(0, waterService.selectBySeason(userId));
        if(type == 2) return jsonUtil.getJSONString(0, powerService.selectBySeason(userId));
        return jsonUtil.getJSONString(0, gasService.selectBySeason(userId));
    }

    @CommonLimit
    @GetMapping(value = "/resource/year/", produces = {"application/json;charset=UTF-8"})
    public String getResouceByYear(int type, int userId) {
        if(type == 1) return jsonUtil.getJSONString(0, waterService.seleccByYear(userId));
        if(type == 2) return jsonUtil.getJSONString(0, powerService.seleccByYear(userId));
        return jsonUtil.getJSONString(0, gasService.seleccByYear(userId));
    }



}
