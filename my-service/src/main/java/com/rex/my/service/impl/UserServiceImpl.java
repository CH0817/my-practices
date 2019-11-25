package com.rex.my.service.impl;

import com.rex.my.dao.mapper.mysql.UserMapper;
import com.rex.my.entity.mysql.User;
import com.rex.my.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserMapper mapper;

    @Autowired
    public UserServiceImpl(UserMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean insert(User user) {
        System.out.println(mapper);
        return mapper.insert(user) > 0;
    }
}
