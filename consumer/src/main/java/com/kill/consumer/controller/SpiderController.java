package com.kill.consumer.controller;

import com.crossoverjie.distributed.annotation.CommonLimit;
import com.kill.consumer.service.impl.ProductServiceImpl;
import com.kill.consumer.util.jsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ComponentScan(value = "com.kill.consumer.config")
@ComponentScan(value = "com.crossoverjie.distributed.intercept")
@RestController
@RequestMapping("/spider")
public class SpiderController {

    @Autowired
    ProductServiceImpl productService;

    /**
     * 调用python脚本做爬虫
     * @param msg 爬虫关键词
     * @return 爬取内容
     */
    @CommonLimit
    @GetMapping(value = "", produces = {"application/json;charset=UTF-8"})
    public String getProduct(String msg) {
        return jsonUtil.getJSONString(0, productService.getSpider(msg));
    }
}
