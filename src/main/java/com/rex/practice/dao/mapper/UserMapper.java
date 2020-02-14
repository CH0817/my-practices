package com.rex.practice.dao.mapper;

import com.rex.practice.dao.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    int insertSelective(User entity);

    List<User> selectAll();

    User selectByPrimaryKey(String id);

    int update2DeleteByPrimaryKey(String id);

    int updateSelectiveByPrimaryKey(User entity);

    User findByEmail(String email);

}