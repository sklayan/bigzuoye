package org.example.bigzuoye.controller;

import org.example.bigzuoye.common.Result;
import org.example.bigzuoye.service.LikeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Resource
    private LikeService likeService;

    @PostMapping("/{articleId}")
    public Result<?> like(@PathVariable Long articleId) {
        likeService.like(articleId);
        return Result.success();
    }

    @DeleteMapping("/{articleId}")
    public Result<?> unlike(@PathVariable Long articleId) {
        likeService.unlike(articleId);
        return Result.success();
    }
}
