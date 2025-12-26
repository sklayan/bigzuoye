package org.example.bigzuoye.controller;

import org.example.bigzuoye.common.Result;
import org.example.bigzuoye.entity.Comment;
import org.example.bigzuoye.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    // 发表评论
    @PostMapping
    public Result<?> add(@RequestBody Comment comment) {
        commentService.addComment(comment);
        return Result.success();
    }

    // 获取文章评论
    @GetMapping("/article/{articleId}")
    public Result<?> listByArticle(@PathVariable Long articleId) {
        return Result.success(commentService.listByArticle(articleId));
    }

    // 删除评论
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        commentService.delete(id);
        return Result.success();
    }
}
