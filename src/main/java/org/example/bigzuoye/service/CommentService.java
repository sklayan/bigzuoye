package org.example.bigzuoye.service;

import org.example.bigzuoye.entity.Comment;

import java.util.List;

public interface CommentService {

    void add(Comment comment);

    List<Comment> listByArticle(Long articleId);

    void delete(Long commentId, Long userId);
}
