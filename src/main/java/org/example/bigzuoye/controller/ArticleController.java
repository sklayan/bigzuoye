package org.example.bigzuoye.controller;

import org.example.bigzuoye.common.Result;
import org.example.bigzuoye.entity.Article;
import org.example.bigzuoye.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    // 新增文章
    @PostMapping
    public Result<?> add(@RequestBody Article article) {
        articleService.addArticle(article);
        return Result.success();
    }

    // 修改文章
    @PutMapping
    public Result<?> update(@RequestBody Article article) {
        articleService.updateArticle(article);
        return Result.success();
    }

    // 删除文章
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return Result.success();
    }

    // 文章详情
    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id) {
        return Result.success(articleService.getById(id));
    }

    // 分页查询
    @GetMapping("/list")
    public Result<?> list(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String keyword
    ) {
        return Result.success(articleService.list(page, size, keyword));
    }
}
