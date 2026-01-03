package org.example.bigzuoye.service.impl;

import org.example.bigzuoye.entity.Article;
import org.example.bigzuoye.mapper.ArticleMapper;
import org.example.bigzuoye.service.ArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public void create(Article article) {
        articleMapper.insert(article);
    }

    @Override
    public void update(Article article) {
        articleMapper.update(article);
    }

    @Override
    public void delete(Long articleId, Long userId) {
        Article article = articleMapper.findById(articleId);
        if (article == null) {
            throw new IllegalArgumentException("文章不存在");
        }
        if (!article.getUserId().equals(userId)) {
            throw new IllegalArgumentException("无权限删除该文章");
        }
        articleMapper.deleteById(articleId);
    }

    @Override
    public Article getById(Long articleId) {
        articleMapper.increaseViewCount(articleId);
        return articleMapper.findById(articleId);
    }

    @Override
    public List<Article> listByUser(Long userId) {
        return articleMapper.findByUserId(userId);
    }

    @Override
    public List<Article> search(Long userId, String keyword) {
        return articleMapper.searchByTitle(userId, keyword);
    }

    @Override
    public void increaseView(Long articleId) {
        articleMapper.increaseViewCount(articleId);
    }

    public List<Article> list(String keyword, Long userId, boolean personal) {
        if (personal) {
            if (keyword == null || keyword.isBlank()) {
                return articleMapper.findByUserId(userId);
            }
            return articleMapper.searchByTitle(userId, keyword);
        } else {
            if (keyword == null || keyword.isBlank()) {
                return articleMapper.findAll();
            }
            return articleMapper.searchAllByTitle(keyword);
        }
    }

}
