package org.example.bigzuoye.controller;

import org.example.bigzuoye.common.Result;
import org.example.bigzuoye.entity.Tag;
import org.example.bigzuoye.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Resource
    private TagService tagService;

    @PostMapping
    public Result<?> add(@RequestBody Tag tag) {
        tagService.add(tag);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(tagService.listB());
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        tagService.delete(id);
        return Result.success();
    }
}
