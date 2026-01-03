package org.example.bigzuoye.controller;

import org.example.bigzuoye.common.Result;
import org.example.bigzuoye.common.ResultCode;
import org.example.bigzuoye.entity.Article;
import org.example.bigzuoye.mapper.ArticleMapper;
import org.example.bigzuoye.security.JwtUtil;
import org.example.bigzuoye.security.UserContext;
import org.example.bigzuoye.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public Result<?> create(@RequestBody Article article) {
        article.setUserId(UserContext.getUserId());
        articleService.create(article);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Article article) {
        article.setUserId(UserContext.getUserId());
        articleService.update(article);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String token
    ) {
        if (token == null || token.isBlank()) {
            return Result.error(ResultCode.UNAUTHORIZED);
        }

        // ⭐ 关键一行：去掉 Bearer
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Long userId = jwtUtil.getUserIdFromToken(token);
        articleService.delete(id, userId);
        return Result.success();
    }



    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        Article article = articleService.getById(id);
        return Result.success(article);
    }

    @GetMapping("/list")
    public Result<?> list(
            @RequestParam(required = false) String keyword
    ) {
        List<Article> list;

        if (keyword == null || keyword.isBlank()) {
            // 🔴 公开博客
            list = articleMapper.findAll();
        } else {
            list = articleMapper.searchAllByTitle(keyword);
        }

        return Result.success(list);
    }

    @GetMapping("/my")
    public Result<?> myArticles(
            @RequestHeader(value = "Authorization", required = false) String token)
    {
        System.out.println("Authorization header = [" + token + "]");
        // 1️⃣ 判空即可，不判断 Bearer
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

        // 3️⃣ 查询
        List<Article> list = articleMapper.findByUserId(userId);
        return Result.success(list);
    }

}
