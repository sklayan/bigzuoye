package org.example.bigzuoye.mapper;

import org.example.bigzuoye.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    int insert(Comment comment);

    List<Comment> findByArticleId(Long articleId);

    int deleteById(Long id);
}
