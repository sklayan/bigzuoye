package org.example.bigzuoye.controller;

import org.example.bigzuoye.common.Result;
import org.example.bigzuoye.common.ResultCode;
import org.example.bigzuoye.security.JwtUtil;
import org.example.bigzuoye.service.ArticleLikeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/artlike")
public class ArticleLikeController {

    @Resource
    private ArticleLikeService articleLikeService;

    @Resource
    private JwtUtil jwtUtil;

    @PostMapping("/{id}/like")
    public Result<ArticleLikeService.LikeResult> toggleLike(
            @PathVariable("id") Long articleId,
            @RequestHeader(value = "Authorization", required = false) String token
    ) {
        if (token == null || token.isBlank()) {
            return Result.error(ResultCode.UNAUTHORIZED);
        }

        // 2️⃣ 直接解析（适配你现在的 JwtUtil）
        Long userId;
        try {
            userId = jwtUtil.getUserIdFromToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ResultCode.UNAUTHORIZED);
        }

        ArticleLikeService.LikeResult result = articleLikeService.toggleLike(articleId, userId);
        return Result.success(result);
    }
}
