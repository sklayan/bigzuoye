package org.example.bigzuoye.service.impl;

import org.example.bigzuoye.entity.Tag;
import org.example.bigzuoye.mapper.TagMapper;
import org.example.bigzuoye.service.TagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Override
    public Tag getOrCreate(Long userId, String name) {
        Tag tag = tagMapper.findByName(userId, name);
        if (tag != null) {
            return tag;
        }
        Tag newTag = new Tag();
        newTag.setUserId(userId);
        newTag.setName(name);
        tagMapper.insert(newTag);
        return newTag;
    }

    @Override
    public List<Tag> listByUser(Long userId) {
        return tagMapper.findByUserId(userId);
    }
}
