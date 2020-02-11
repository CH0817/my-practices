package com.rex.practice.service.impl;

import com.rex.practice.dao.mapper.UserMapper;
import com.rex.practice.dao.model.User;
import com.rex.practice.model.vo.Login;
import com.rex.practice.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private UserMapper userMapper;

    @Autowired
    public LoginServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public boolean login(Login login) {
        logger.info("input login: {}", login);
        User user = userMapper.findByEmail(login.getEmail());
        logger.info("found user: {}", user);
        return Objects.nonNull(user) && login.getPassword().equals(user.getPassword());
    }

}
