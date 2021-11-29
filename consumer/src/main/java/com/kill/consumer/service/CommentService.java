package com.kill.consumer.service;

import com.kill.api.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentByEntity(Integer entityId, Integer entityType);

    int addComment(Comment comment);

    int getCommentCount(Integer entityId, Integer entityType);

    boolean deleteComment(Integer commentId);

    Comment getCommentById(Integer id);
}
