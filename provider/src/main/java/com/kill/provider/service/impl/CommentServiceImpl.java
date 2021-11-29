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
    public List<Comment> getCommentByEntity(Integer entityId, Integer entityType) {
        if(entityId == null || entityType == null) {
            throw new NullPointerException("entityId or entityType can't be empty");
        }
        return commentdao.selectCommentByEntity(entityId, entityType);
    }


    @Override
    public int addComment(Comment comment) {
        if(comment == null) {
            throw new NullPointerException("comment can't be null");
        }
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        return commentdao.addcomment(comment) > 0 ? comment.getId() : 0;
    }

    @Override
    public int getCommentCount(Integer entityId, Integer entityType) {
        if(entityId == null || entityType == null) {
            throw new NullPointerException("entityId or entityType can't be empty");
        }
        return commentdao.getCommentCount(entityId, entityType);
    }

    @Override
    public boolean deleteComment(Integer commentId) {
        if(commentId == null) {
            throw new NullPointerException("commentId can't be empty");
        }
        return commentdao.updateStatus(commentId, 1) > 0;
    }

    @Override
    public Comment getCommentById(Integer id) {
        if(id == null) {
            throw new NullPointerException("commentId can't be empty");
        }
        return commentdao.selectById(id);
    }

}
