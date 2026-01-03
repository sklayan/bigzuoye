package org.example.bigzuoye.controller;

import org.example.bigzuoye.common.Result;
import org.example.bigzuoye.common.ResultCode;
import org.example.bigzuoye.dto.LoginDTO;
import org.example.bigzuoye.dto.RegisterDTO;
import org.example.bigzuoye.entity.User;
import org.example.bigzuoye.security.JwtUtil;
import org.example.bigzuoye.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private UserService userService;

    @Resource
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginDTO dto) {
        User user = userService.login(dto.getUsername(), dto.getPassword());
        String token = jwtUtil.generateToken(user.getId());
        return Result.success(token);
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            return Result.error(ResultCode.valueOf("两次密码不一致"));
        }
        userService.register(dto.getUsername(), dto.getPassword(), null);
        return Result.success();
    }
}
