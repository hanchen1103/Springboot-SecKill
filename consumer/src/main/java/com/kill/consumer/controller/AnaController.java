package com.kill.consumer.controller;

import com.kill.consumer.service.impl.AnaServiceImpl;
import com.kill.consumer.util.jsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analysis")
public class AnaController {

    @Autowired
    AnaServiceImpl anaService;

    /**
     * 新增点赞
     * @return 返回本月新增点赞的数量
     */
    @GetMapping(value = "/like",produces = {"application/json;charset=UTF-8"})
    public String newLike(){
        return jsonUtil.getJSONString(0, anaService.getLike());
    }

    /**
     * 新增用户
     * @return 返回新增用户的数量
     */
    @GetMapping(value = "/newProfile",produces = {"application/json;charset=UTF-8"})
    public String newPro(){
        return jsonUtil.getJSONString(0, anaService.getNewProfile());
    }

    /**
     * 统计当前在线人数
     * @return 整数值，表示人数
     */
    @GetMapping(value = "/login",produces = {"application/json;charset=UTF-8"})
    public String nowLogin() {
        return jsonUtil.getJSONString(0, anaService.selectLoginNow());
    }

    /**
     * 比较今日与昨日日活量
     * @return 今日减昨日，如果为负数代表下降，否则为上升
     */
    @GetMapping(value = "/compare/daily",produces = {"application/json;charset=UTF-8"})
    public String compareDaily() {
        return jsonUtil.getJSONString(0, anaService.selectNowDays() - anaService.selectYestDays());
    }

    /**
     * 比较本月与上个月日活量
     * @return 本月减上个月，如果为负数代表下降，否则为上升
     */
    @GetMapping(value = "/compare/month",produces = {"application/json;charset=UTF-8"})
    public String compareMonth() {
        return jsonUtil.getJSONString(0, anaService.selectMonth() - anaService.selectLastMonth());
    }

    /**
     * 统计以前的日活量
     * @return 返回List<Map<String,Object>>类型包装成的json字符串,map中包含日期和每日的登录量
     */
    @GetMapping(value = "/daily", produces = {"application/json;charset=UTF-8"})
    public String daily() {
        return jsonUtil.getJSONString(0, anaService.daily());
    }


    /**
     * 统计所有用户的所在地
     * @return 返回List<Map<String,Object>>类型包装成json字符串，map中包含每个地区所在用户的个数
     */
    @GetMapping(value = "/profileLocation", produces = {"application/json;charset=UTF-8"})
    public String locationPofile() {
        return jsonUtil.getJSONString(0, anaService.profileLocation());
    }

    /**
     * 统计热门搜索词
     * @return 返回List<Map<String,Object>>类型包装成json字符串，map中包含历史搜索词中统计一百条搜索最多的词语和它们出现的次数
     */
    @GetMapping(value = "/hotsearch", produces = {"application/json;charset=UTF-8"})
    public String HotSearch() {
        return jsonUtil.getJSONString(0, anaService.HotSearch());
    }


}