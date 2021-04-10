package com.kill.consumer.controller;


import com.kill.consumer.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @RequestMapping("/add/a={a}")
    public String getCost(@PathVariable("a") int a){
        return "cost :"+ productService.getCost(a);
    }

}
