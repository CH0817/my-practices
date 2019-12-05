package com.rex.my.business.service.impl;

import com.rex.my.business.service.LoginService;
import com.rex.my.dao.mapper.primary.UserMapper;
import com.rex.my.model.dao.primary.User;
import com.rex.my.model.vo.Login;
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
