package com.rex.practice.service.impl;

import com.rex.practice.dao.mapper.UserMapper;
import com.rex.practice.dao.model.User;
import com.rex.practice.model.input.Register;
import com.rex.practice.service.TokenService;
import com.rex.practice.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private TokenService tokenService;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Override
    public boolean addUser(Register register) {
        User user = new User();
        BeanUtils.copyProperties(register, user);
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        user.setCreateDate(new Date());
        boolean result = userMapper.insertSelective(user) == 1;
        if (result) {
            tokenService.createRegisterToken(register.getEmail());
        }
        return result;
    }

    @Override
    public boolean isUserExists(String email) {
        return Objects.nonNull(userMapper.findByEmail(email));
    }

}
