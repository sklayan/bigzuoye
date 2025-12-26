package org.example.bigzuoye.service;

import org.example.bigzuoye.entity.Article;

import java.util.List;

public interface ArticleService {

    void create(Article article);

    void update(Article article);

    void delete(Long articleId, Long userId);

    Article getById(Long articleId);

    List<Article> listByUser(Long userId);

    List<Article> search(Long userId, String keyword);

    void increaseView(Long articleId);
}
