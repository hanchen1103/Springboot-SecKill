package com.kill.consumer.controller;

import com.crossoverjie.distributed.annotation.CommonLimit;
import com.kill.api.model.Gas;
import com.kill.api.model.power;
import com.kill.api.model.water;
import com.kill.consumer.service.impl.GasServiceImpl;
import com.kill.consumer.service.impl.PowerServiceImpl;
import com.kill.consumer.service.impl.WaterServiceImpl;
import com.kill.consumer.util.RedisKeyUtil;
import com.kill.consumer.util.jsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    RedisTemplate<String, String> redisTemplate;

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
        if(type == 1) {
            if(redisTemplate.opsForValue().get(RedisKeyUtil.getWaterMonth(userId)) != null)
                return redisTemplate.opsForValue().get(RedisKeyUtil.getWaterMonth(userId));
            String res = jsonUtil.getJSONString(0, waterService.selectByMonth(userId));
            redisTemplate.opsForValue().set(RedisKeyUtil.getWaterMonth(userId), res, 10 * 60, TimeUnit.MINUTES);
            return res;
        }
        if(type == 2) {
            if(redisTemplate.opsForValue().get(RedisKeyUtil.getPowerMonth(userId)) != null)
                return redisTemplate.opsForValue().get(RedisKeyUtil.getPowerMonth(userId));
            String res = jsonUtil.getJSONString(0, powerService.selectByMonth(userId));
            redisTemplate.opsForValue().set(RedisKeyUtil.getPowerMonth(userId), res, 10 * 60, TimeUnit.MINUTES);
            return res;
        }
        if(redisTemplate.opsForValue().get(RedisKeyUtil.getGasMonth(userId)) != null)
            return redisTemplate.opsForValue().get(RedisKeyUtil.getGasMonth(userId));
        String res = jsonUtil.getJSONString(0, gasService.selectByMonth(userId));
        redisTemplate.opsForValue().set(RedisKeyUtil.getGasMonth(userId), res, 10 * 60, TimeUnit.MINUTES);
        return res;
    }

    @CommonLimit
    @GetMapping(value = "/resource/season/", produces = {"application/json;charset=UTF-8"})
    public String getResouceBySeason(int type, int userId) {
        if(type == 1) {
            if(redisTemplate.opsForValue().get(RedisKeyUtil.getWaterSeason(userId)) != null)
                return redisTemplate.opsForValue().get(RedisKeyUtil.getWaterSeason(userId));
            String res = jsonUtil.getJSONString(0, waterService.selectByMonth(userId));
            redisTemplate.opsForValue().set(RedisKeyUtil.getWaterSeason(userId), res, 10 * 60, TimeUnit.MINUTES);
            return res;
        }
        if(type == 2) {
            if(redisTemplate.opsForValue().get(RedisKeyUtil.getPowerSeason(userId)) != null)
                return redisTemplate.opsForValue().get(RedisKeyUtil.getPowerSeason(userId));
            String res = jsonUtil.getJSONString(0, powerService.selectByMonth(userId));
            redisTemplate.opsForValue().set(RedisKeyUtil.getPowerSeason(userId), res, 10 * 60, TimeUnit.MINUTES);
            return res;
        }
        if(redisTemplate.opsForValue().get(RedisKeyUtil.getGasSeason(userId)) != null)
            return redisTemplate.opsForValue().get(RedisKeyUtil.getGasSeason(userId));
        String res = jsonUtil.getJSONString(0, gasService.selectByMonth(userId));
        redisTemplate.opsForValue().set(RedisKeyUtil.getGasSeason(userId), res, 10 * 60, TimeUnit.MINUTES);
        return res;
    }

    @CommonLimit
    @GetMapping(value = "/resource/year/", produces = {"application/json;charset=UTF-8"})
    public String getResouceByYear(int type, int userId) {
        if(type == 1) return jsonUtil.getJSONString(0, waterService.seleccByYear(userId));
        if(type == 2) return jsonUtil.getJSONString(0, powerService.seleccByYear(userId));
        return jsonUtil.getJSONString(0, gasService.seleccByYear(userId));
    }



}
