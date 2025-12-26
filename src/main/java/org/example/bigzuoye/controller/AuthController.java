package org.example.bigzuoye.controller;

import org.example.bigzuoye.common.Result;
import org.example.bigzuoye.dto.LoginDTO;
import org.example.bigzuoye.dto.RegisterDTO;
import org.example.bigzuoye.service.AuthService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginDTO dto) {
        return Result.success(authService.login(dto));
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody RegisterDTO dto) {
        authService.register(dto);
        return Result.success();
    }
}
