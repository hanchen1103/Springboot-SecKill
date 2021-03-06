package com.kill.consumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.kill.api.model.Comment;
import com.kill.consumer.service.CommentService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Reference
    com.kill.api.service.CommentService commentService;


    @Override
    public List<Comment> getCommentByEntity(Integer entityId, Integer entityType) {
        return commentService.getCommentByEntity(entityId, entityType);
    }

    @Override
    public int addComment(Comment comment) {
        return commentService.addComment(comment);
    }

    @Override
    public int getCommentCount(Integer entityId, Integer entityType) {
        return commentService.getCommentCount(entityId, entityType);
    }

    @Override
    public boolean deleteComment(Integer commentId) {
        return commentService.deleteComment(commentId);
    }

    @Override
    public Comment getCommentById(Integer id) {
        return commentService.getCommentById(id);
    }
}
