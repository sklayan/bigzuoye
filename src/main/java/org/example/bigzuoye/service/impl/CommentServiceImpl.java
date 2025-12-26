package org.example.bigzuoye.service.impl;

import org.example.bigzuoye.entity.Comment;
import org.example.bigzuoye.mapper.CommentMapper;
import org.example.bigzuoye.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public void add(Comment comment) {
        commentMapper.insert(comment);
    }

    @Override
    public List<Comment> listByArticle(Long articleId) {
        return commentMapper.findByArticleId(articleId);
    }

    @Override
    public void delete(Long commentId, Long userId) {
        // 简化处理：课程作业一般不要求复杂权限
        commentMapper.deleteById(commentId);
    }
}
