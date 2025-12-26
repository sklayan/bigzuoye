package org.example.bigzuoye.controller;

import org.example.bigzuoye.common.Result;
import org.example.bigzuoye.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/me")
    public Result<?> currentUser() {
        return Result.success(userService.getCurrentUser());
    }
}
