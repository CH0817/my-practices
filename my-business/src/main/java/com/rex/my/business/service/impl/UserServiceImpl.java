package com.rex.my.business.service.impl;

import com.rex.my.business.service.UserService;
import com.rex.my.dao.mapper.primary.UserMapper;
import com.rex.my.model.dao.primary.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User findByEmail(String email) {
        User result = userMapper.findByEmail(email);
        result.setPassword(null);
        return result;
    }

}
