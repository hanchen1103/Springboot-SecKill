package com.kill.consumer.controller;

import com.kill.consumer.service.impl.LikeServiceImpl;
import com.kill.consumer.util.jsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class LikeController {

    @Autowired
    LikeServiceImpl likeServiceimpl;

    private static final Logger logger = LoggerFactory.getLogger(LikeController.class);

    /**
     *
     * @param map entityType, 1:product, 2:user, 3:comment, 4:hotel
     * @return json
     */
    @PostMapping(value = "/like", produces = {"application/json;charset=UTF-8"})
    public String collect(@RequestBody Map<String, String> map) {
        int entityId = Integer.parseInt(map.get("entityId"));
        int userId = Integer.parseInt(map.get("userId"));
        int entityType = Integer.parseInt(map.get("entityType"));
        return jsonUtil.getJSONString(0, likeServiceimpl.like(userId, entityType, entityId));
    }

    /**
     * 取消喜欢
     * @param map entityType, 1:product, 2:user, 3:comment, 4:hotel, userId:自身id，entityId：对象id
     * @return json
     */
    @PostMapping(value = "/disLike", produces = {"application/json;charset=UTF-8"})
    public String uncollect(@RequestBody Map<String, String> map) {
        int entityId = Integer.parseInt(map.get("entityId"));
        int userId = Integer.parseInt(map.get("userId"));
        int entityType = Integer.parseInt(map.get("entityType"));
        return jsonUtil.getJSONString(0, likeServiceimpl.disLike(userId, entityType, entityId));
    }

}
