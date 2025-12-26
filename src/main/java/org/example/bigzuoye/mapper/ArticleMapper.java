package org.example.bigzuoye.mapper;

import org.example.bigzuoye.entity.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {

    int insert(Article article);

    int update(Article article);

    int deleteById(Long id);

    Article findById(Long id);

    List<Article> findByUserId(Long userId);

    List<Article> searchByTitle(Long userId, String keyword);

    int increaseViewCount(Long id);

    int increaseLikeCount(Long id);

    int decreaseLikeCount(Long id);
}
