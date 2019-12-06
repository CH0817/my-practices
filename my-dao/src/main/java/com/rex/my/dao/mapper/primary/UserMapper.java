package com.rex.my.dao.mapper.primary;

import com.rex.my.model.dao.primary.User;

public interface UserMapper {

    User findByEmail(String email);

}