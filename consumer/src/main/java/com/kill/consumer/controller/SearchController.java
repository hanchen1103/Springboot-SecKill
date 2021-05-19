package com.kill.consumer.controller;

import com.crossoverjie.distributed.annotation.CommonLimit;
import com.kill.consumer.service.impl.SearchServiceImpl;
import com.kill.consumer.util.jsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ComponentScan(value = "com.kill.consumer.config")
@ComponentScan(value = "com.crossoverjie.distributed.intercept")
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchServiceImpl searchService;

    /**
     * 搜索
     * @param type 0 代表商品(name+tag+descri联合搜索) 1 代表用户(根据nickName)
     * @param q 搜索词
     * @return list<stock>或List<profile>
     */
    @CommonLimit
    @GetMapping(value = "", produces = "application/json;charset=UTF-8")
    public String searchstock(int type, String q) {
        if(type == 0) return jsonUtil.getJSONString(0, searchService.searchStock(q));
        return jsonUtil.getJSONString(0, searchService.selectProfile(q));
    }

}
