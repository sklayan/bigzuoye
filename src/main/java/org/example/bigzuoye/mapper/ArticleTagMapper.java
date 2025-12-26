package org.example.bigzuoye.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleTagMapper {

    int insert(Long articleId, Long tagId);

    int deleteByArticleId(Long articleId);
}
