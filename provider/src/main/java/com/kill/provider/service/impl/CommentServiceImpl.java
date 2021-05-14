package com.kill.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.Comment;
import com.kill.api.service.CommentService;
import com.kill.provider.mapper.CommentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
@org.springframework.stereotype.Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentDAO commentdao;


    @Override
    public List<Comment> getCommentByEntity(int entityId, int entityType) {
        return commentdao.selectCommentByEntity(entityId, entityType);
    }
    @Override
    public int addComment(Comment comment) {
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        return commentdao.addcomment(comment) > 0 ? comment.getId() : 0;
    }
    @Override
    public int getCommentCount(int entityId, int entityType) {
        return commentdao.getCommentCount(entityId, entityType);
    }

    @Override
    public boolean deleteComment(int commentId) {
        return commentdao.updateStatus(commentId, 1) > 0;
    }

    @Override
    public Comment getCommentById(int id) {
        return commentdao.selectById(id);
    }

}
