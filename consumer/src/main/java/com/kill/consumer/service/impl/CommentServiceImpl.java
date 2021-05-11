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
    public List<Comment> getCommentByEntity(int entityId, int entityType) {
        return null;
    }

    @Override
    public int addComment(Comment comment) {
        return 0;
    }

    @Override
    public int getCommentCount(int entityId, int entityType) {
        return 0;
    }

    @Override
    public boolean deleteComment(int commentId) {
        return false;
    }

    @Override
    public Comment getCommentById(int id) {
        return null;
    }
}
