package org.example.bigzuoye.service.impl;

import org.example.bigzuoye.entity.User;
import org.example.bigzuoye.mapper.UserMapper;
import org.example.bigzuoye.service.UserService;
import org.example.bigzuoye.util.PasswordUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        if (!PasswordUtil.match(password, user.getPassword())) {
            throw new IllegalArgumentException("密码错误");
        }
        return user;
    }

    @Override
    public void register(String username, String password, String email) {
        if (userMapper.findByUsername(username) != null) {
            throw new IllegalArgumentException("用户名已存在");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(PasswordUtil.encode(password));
        user.setEmail(email);
        userMapper.insert(user);
    }

    @Override
    public User getById(Long userId) {
        return userMapper.findById(userId);
    }
}
