package com.kill.consumer.controller;

import com.kill.consumer.service.impl.SearchServiceImpl;
import com.kill.consumer.util.jsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchServiceImpl searchService;

    @GetMapping(value = "", produces = "application/json;charset=UTF-8")
    public String searchstock(int type, String q) {
        if(type == 0) return jsonUtil.getJSONString(0, searchService.searchStock(q));
        return jsonUtil.getJSONString(0, searchService.selectProfile(q));
    }

}
