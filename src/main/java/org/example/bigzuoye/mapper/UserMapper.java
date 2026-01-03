package org.example.bigzuoye.mapper;

import org.example.bigzuoye.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    User findByUsername(@Param("username") String username);

    int insert(User user);

    User findById(@Param("id") Long id);
}
