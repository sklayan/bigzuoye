package org.example.bigzuoye.service;

import org.example.bigzuoye.entity.Tag;

import java.util.List;

public interface TagService {

    Tag getOrCreate(Long userId, String name);

    List<Tag> listByUser(Long userId);
}
