package org.example.bigzuoye.mapper;

import org.example.bigzuoye.entity.ArticleLike;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleLikeMapper {

    int insert(ArticleLike like);

    int delete(Long articleId, Long userId);

    ArticleLike find(Long articleId, Long userId);
}
