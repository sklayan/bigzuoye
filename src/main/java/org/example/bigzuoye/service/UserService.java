package org.example.bigzuoye.service;

import org.example.bigzuoye.entity.User;

public interface UserService {

    User login(String username, String password);

    void register(String username, String password, String email);

    User getById(Long userId);
}
