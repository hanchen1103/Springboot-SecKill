package com.kill.consumer.service;

import com.kill.api.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentByEntity(int entityId, int entityType);

    int addComment(Comment comment);

    int getCommentCount(int entityId, int entityType);

    boolean deleteComment(int commentId);

    Comment getCommentById(int id);
}
