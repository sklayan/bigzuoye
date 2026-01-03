package org.example.bigzuoye.service;

import org.example.bigzuoye.entity.ArticleLike;

public interface ArticleLikeService {

    /**
     * 用户点赞文章，如果已点赞则取消点赞
     * @param articleId 文章ID
     * @param userId 用户ID
     * @return 最新的点赞数量和当前用户是否点赞
     */
    LikeResult toggleLike(Long articleId, Long userId);

    /**
     * 获取文章点赞数量
     */
    int countLikes(Long articleId);

    /**
     * 判断用户是否点赞
     */
    boolean isLiked(Long articleId, Long userId);

    class LikeResult {
        private boolean liked;
        private int likeCount;

        public LikeResult(boolean liked, int likeCount) {
            this.liked = liked;
            this.likeCount = likeCount;
        }

        public boolean isLiked() { return liked; }
        public int getLikeCount() { return likeCount; }
    }
}
