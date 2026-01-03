package org.example.bigzuoye.controller;

import org.example.bigzuoye.common.Result;
import org.example.bigzuoye.entity.Tag;
import org.example.bigzuoye.security.UserContext;
import org.example.bigzuoye.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Resource
    private TagService tagService;

    @PostMapping
    public Result<?> create(@RequestBody Tag tag) {
        tag.setUserId(UserContext.getUserId());
        Tag t = tagService.getOrCreate(UserContext.getUserId(), tag.getName());
        return Result.success(t);
    }

    @GetMapping("/list")
    public Result<?> list() {
        List<Tag> tags = tagService.listByUser(UserContext.getUserId());
        return Result.success(tags);
    }
}
