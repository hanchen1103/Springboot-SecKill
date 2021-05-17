package com.kill.consumer.controller;

import com.kill.api.model.Comment;
import com.kill.api.model.Profile;
import com.kill.consumer.service.impl.CommentServiceImpl;
import com.kill.consumer.service.impl.ProfileServiceImpl;
import com.kill.consumer.util.jsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    CommentServiceImpl commentService;

    @Autowired
    ProfileServiceImpl profileService;

    /**
     * 发表评论
     * @param jsonMap content: 评论内容 , entityId:评论对象id，userId:自身id,entityType:评论对象类型
     * @return jsonstring(状态码)
     */
    @PostMapping(value = "/add",produces = {"application/json;charset=UTF-8"})
    public String addComment(@RequestBody Map<String, Object> jsonMap) {
        try {
            Comment comment = new Comment();
            comment.setContent(String.valueOf(jsonMap.get("content")));
            comment.setCreateDate(new Date());
            int entityId = (int) jsonMap.get("entityId");
            comment.setEntityId(entityId);
            comment.setUserId((int) jsonMap.get("userId"));
            comment.setEntityType((int) jsonMap.get("entityType")); //评论商品
            commentService.addComment(comment);

        } catch (Exception e) {
            logger.error("error" + e.getMessage());
            return jsonUtil.getJSONString(1);
        }
        return jsonUtil.getJSONString(0);
    }

    /**
     * 通过type和id获取评论详情
     * @param entityType 类型
     * @param entityId id
     * @return 包含此条评论主体和相关用户信息
     */
    @GetMapping(value = "/entityType={entityType}/entityId={entityId}", produces = {"application/json;charset=UTF-8"})
    public String getComment(@PathVariable int entityType, @PathVariable int entityId) {
        List<Comment> commentList = commentService.getCommentByEntity(entityId, entityType);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Comment comment : commentList) {
            int userId = comment.getUserId();
            Profile profile = profileService.selectByUserId(userId);
            Map<String, Object> m = new HashMap<>();
            m.put("profile", profile);
            m.put("comment", comment);
            list.add(m);
        }
        return jsonUtil.getJSONString(0, list);
    }

    /**
     * 通过id获取评论
     * @param commentId 评论id
     * @return 包含此条评论主体和相关用户信息
     */
    @GetMapping(value = "/{commentId}",produces = {"application/json;charset=UTF-8"})
    public String getCommentEntity(@PathVariable int commentId) {
        Comment comment = commentService.getCommentById(commentId);
        if(comment == null) return jsonUtil.getJSONString(999, "comment is null");
        Map<String, Object> map = new HashMap<>();
        Profile profile = profileService.selectByUserId(comment.getUserId());
        map.put("comment", comment);
        map.put("profile", profile);
        return jsonUtil.getJSONString(0, map);
    }

    /**
     * 通过评论id删除此条评论
     * @param map map中存放要删除评论的id
     * @return 返回用json包装的code值，如果为0则表示正确，其他则为错误
     */
    @DeleteMapping(value = "/",produces = {"application/json;charset=UTF-8"})
    public String deleteComment(@RequestBody Map<String, Integer> map) {
        int commentId = map.get("commentId");
        boolean res = commentService.deleteComment(commentId);
        return jsonUtil.getJSONString(res ? 0 : 999);
    }
}
