package org.example.bigzuoye.controller;

import org.example.bigzuoye.common.Result;
import org.example.bigzuoye.entity.Comment;
import org.example.bigzuoye.security.UserContext;
import org.example.bigzuoye.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @PostMapping
    public Result<?> add(@RequestBody Comment comment) {
        comment.setUserId(UserContext.getUserId());
        commentService.add(comment);
        return Result.success();
    }

    @GetMapping("/article/{articleId}")
    public Result<?> listByArticle(@PathVariable Long articleId) {
        List<Comment> list = commentService.listByArticle(articleId);
        return Result.success(list);
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        commentService.delete(id, UserContext.getUserId());
        return Result.success();
    }
}
