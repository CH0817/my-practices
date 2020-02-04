package com.rex.practice.dao.mapper.primary;

import com.rex.practice.dao.model.primary.User;

import java.util.List;

public interface UserMapper {

    int insertSelective(User entity);

    List<User> selectAll();

    User selectByPrimaryKey(String id);

    int update2DeleteByPrimaryKey(String id);

    int updateSelectiveByPrimaryKey(User entity);

    User findByEmail(String email);

}