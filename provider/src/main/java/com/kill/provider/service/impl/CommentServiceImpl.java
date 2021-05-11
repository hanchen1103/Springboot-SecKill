package com.kill.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.Comment;
import com.kill.provider.mapper.CommentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
@org.springframework.stereotype.Service
public class CommentServiceImpl {

    @Autowired
    CommentDAO commentdao;


    public List<Comment> getCommentByEntity(int entityId, int entityType) {
        return commentdao.selectCommentByEntity(entityId, entityType);
    }

    public int addComment(Comment comment) {
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        return commentdao.addcomment(comment) > 0 ? comment.getId() : 0;
    }

    public int getCommentCount(int entityId, int entityType) {
        return commentdao.getCommentCount(entityId, entityType);
    }

    public boolean deleteComment(int commentId) {
        return commentdao.updateStatus(commentId, 1) > 0;
    }

    public Comment getCommentById(int id) {
        return commentdao.selectById(id);
    }

}
