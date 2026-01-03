package org.example.bigzuoye.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.bigzuoye.entity.ArticleLike;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleLikeMapper {

    int insert(ArticleLike like);

    int delete(@Param("articleId") Long articleId,
               @Param("userId") Long userId);

    ArticleLike find(@Param("articleId") Long articleId,
                     @Param("userId") Long userId);

    int countByArticleId(@Param("articleId") Long articleId);

}
