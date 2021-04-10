package com.kill.consumer.controller;

import com.kill.api.model.Gas;
import com.kill.api.model.Profile;
import com.kill.api.model.power;
import com.kill.api.model.water;
import com.kill.consumer.service.GasService;
import com.kill.consumer.service.impl.*;
import com.kill.consumer.util.jsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@RestController
public class testController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    LoginServiceImpl loginService;

    @Autowired
    ProfileServiceImpl profileService;

    Random random = new Random();

    @Autowired
    WaterServiceImpl waterService;

    @Autowired
    GasServiceImpl gasService;

    @Autowired
    PowerServiceImpl powerService;

    @GetMapping(value = "test", produces = "application/json;charset=UTF-8")
    public String reg() {
        for(int i = 0; i < 1000; i ++) {
            water w = new water();
            w.setAccuracy(random.nextDouble() * 1000);
            w.setCreateDate(new Date());
            w.setUserId((random.nextInt(100)));
            w.setFlow(random.nextDouble()  * 1000);
            w.setElectric_frequenct(random.nextDouble()  * 1000);
            Integer res = waterService.addWater(w);
        }
        return jsonUtil.getJSONString(0);
    }

    @GetMapping(value = "test1", produces = "application/json;charset=UTF-8")
    public String power() {
        for(int i = 0; i < 1000; i ++) {
            Gas w = new Gas();
            w.setAccuracy(random.nextDouble() * 1000);
            w.setCreateDate(new Date());
            w.setUserId((random.nextInt(100)));
            w.setFlow(random.nextDouble()  * 1000);
            w.setHumidity(random.nextDouble()  * 1000);
            w.setTemperature(random.nextDouble()  * 1000);
            Integer res = gasService.addGas(w);
        }
        return jsonUtil.getJSONString(0);
    }

    @GetMapping(value = "test2", produces = "application/json;charset=UTF-8")
    public String gas() {
        for(int i = 0; i < 1000; i ++) {
            power w = new power();
            w.setCreateDate(new Date());
            w.setUserId((random.nextInt(100)));
            w.setFlow(random.nextDouble()  * 1000);
            w.setVoltage(random.nextDouble()  * 1000);
            w.setElectric_current(random.nextDouble()  * 1000);
            w.setHumidity(random.nextDouble()  * 1000);
            w.setTemperature(random.nextDouble()  * 1000);
            Integer res = powerService.addpower(w);
        }
        return jsonUtil.getJSONString(0);
    }


}
