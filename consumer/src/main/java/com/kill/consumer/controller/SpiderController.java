package com.kill.consumer.controller;

import com.kill.consumer.service.impl.ProductServiceImpl;
import com.kill.consumer.util.jsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@ComponentScan(value = "com.kill.consumer.config")
@RestController
@RequestMapping("/spider")
public class SpiderController {

    @Autowired
    ProductServiceImpl productService;

    //CountDownLatch countDownLatch = new CountDownLatch(1);


    /**
     * 调用python脚本做爬虫
     * @param msg 爬虫关键词
     * @return 爬取内容
     */
    @GetMapping(value = "", produces = {"application/json;charset=UTF-8"})
    public String getProduct(String msg) {

//        Thread thread = new Thread(() -> {
//            productService.getSpider(msg);
//            countDownLatch.countDown();
//        });
//        thread.start();
//        countDownLatch.await();
        return jsonUtil.getJSONString(0, productService.getSpider(msg));
    }

    @DeleteMapping(value = "")
    public String deleteAll() {
        return jsonUtil.getJSONString(0, productService.delete());
    }

}
