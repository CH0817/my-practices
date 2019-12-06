package com.rex.my.dao.mapper.primary;

import com.rex.my.model.dao.primary.User;

import java.util.List;

public interface UserMapper {

    int insertSelective(User entity);

    User findByEmail(String email);

    List<User> selectAll();

}