package org.example.bigzuoye.mapper;

import org.example.bigzuoye.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {

    Tag findByName(Long userId, String name);

    int insert(Tag tag);

    List<Tag> findByUserId(Long userId);
}
