package com.rex.my.dao.mapper.primary;

import com.rex.my.dao.mapper.base.BaseMapper;
import com.rex.my.model.dao.primary.User;

public interface UserMapper extends BaseMapper<User> {

    User findByEmail(String email);

}