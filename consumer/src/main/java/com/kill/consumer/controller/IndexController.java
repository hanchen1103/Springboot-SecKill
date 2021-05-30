package com.kill.consumer.controller;

import com.kill.consumer.service.StockSer;
import com.kill.consumer.service.impl.StockServiceImpl;
import com.kill.consumer.util.jsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    StockServiceImpl stockService;

    @GetMapping(value = "", produces = {"application/json;charset=UTF-8"})
    public String index() {
        return jsonUtil.getJSONString(0, stockService.selectHot());
    }
}
