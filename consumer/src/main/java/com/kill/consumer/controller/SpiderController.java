package com.kill.consumer.controller;

import com.kill.consumer.service.impl.ProductServiceImpl;
import com.kill.consumer.util.jsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spider")
public class SpiderController {

    @Autowired
    ProductServiceImpl productService;

    @GetMapping(value = "", produces = {"application/json;charset=UTF-8"})
    public String getProduct(String msg) {
        return jsonUtil.getJSONString(0, productService.getSpider(msg));
    }
}
