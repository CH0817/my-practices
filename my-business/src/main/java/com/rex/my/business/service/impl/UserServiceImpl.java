package com.rex.my.business.service.impl;

import com.rex.my.business.service.UserService;
import com.rex.my.dao.entity.primary.User;
import com.rex.my.dao.mapper.primary.UserMapper;
import com.rex.my.dao.mapper.secondary.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private CustomerMapper customerMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, CustomerMapper customerMapper) {
        System.out.println(userMapper);
        System.out.println(customerMapper);
        this.userMapper = userMapper;
        this.customerMapper = customerMapper;
    }

    @Override
    public boolean insert(User user) {
        System.out.println(userMapper);
        return userMapper.insert(user) > 0;
    }
}
