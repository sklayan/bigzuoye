package org.example.bigzuoye.service.impl;

import org.example.bigzuoye.entity.ArticleLike;
import org.example.bigzuoye.mapper.ArticleLikeMapper;
import org.example.bigzuoye.mapper.ArticleMapper;
import org.example.bigzuoye.service.ArticleLikeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class ArticleLikeServiceImpl implements ArticleLikeService {

    @Resource
    private ArticleLikeMapper articleLikeMapper;

    @Resource
    private ArticleMapper articleMapper; // 用于更新文章的likeCount字段，如果有的话

    @Override
    public LikeResult toggleLike(Long articleId, Long userId) {
        ArticleLike existing = articleLikeMapper.find(articleId, userId);
        boolean liked;
        if (existing != null) {
            // 已点赞 -> 取消点赞
            articleLikeMapper.delete(articleId, userId);
            liked = false;
        } else {
            // 未点赞 -> 添加点赞
            ArticleLike like = new ArticleLike();
            like.setArticleId(articleId);
            like.setUserId(userId);
            like.setCreatedAt(LocalDateTime.now());
            articleLikeMapper.insert(like);
            liked = true;
        }

        int likeCount = articleLikeMapper.countByArticleId(articleId); // 如果 Mapper 没有countByArticleId，需要补充
        return new LikeResult(liked, likeCount);
    }

    @Override
    public int countLikes(Long articleId) {
        return articleLikeMapper.countByArticleId(articleId);
    }

    @Override
    public boolean isLiked(Long articleId, Long userId) {
        return articleLikeMapper.find(articleId, userId) != null;
    }
}
