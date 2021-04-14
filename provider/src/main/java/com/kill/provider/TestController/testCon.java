package com.kill.provider.TestController;

import com.alibaba.fastjson.JSON;
import com.kill.api.model.Stock;
import com.kill.provider.config.KafkaProducer;
import com.kill.provider.mapper.SpiderDAO;
import com.kill.provider.mapper.StockDAO;
import com.kill.provider.service.impl.CostServiceImpl;
import com.kill.provider.util.jsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spider")
public class testCon {

    @Autowired
    CostServiceImpl costService;

    @GetMapping("")
    public String test(String msg) {
        return jsonUtil.getJSONString(0, costService.spider(msg));
    }
}
