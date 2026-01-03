package org.example.bigzuoye.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.bigzuoye.entity.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {

    int insert(Article article);

    int update(Article article);

    int deleteById(Long id);

    Article findById(Long id);

    List<Article> findByUserId(@Param("userId")Long userId);

    List<Article> searchByTitle(@Param("userId")Long userId, @Param("keyword")String keyword);

    int increaseViewCount(Long id);

    int increaseLikeCount(Long id);

    int decreaseLikeCount(Long id);

    List<Article> findAll();

    List<Article> searchAllByTitle(@Param("keyword") String keyword);

}
