package com.rex.practice.service.impl;

import com.rex.practice.dao.mapper.UserMapper;
import com.rex.practice.dao.model.User;
import com.rex.practice.model.input.Register;
import com.rex.practice.service.EmailService;
import com.rex.practice.service.TokenService;
import com.rex.practice.service.UserService;
import com.rex.practice.service.base.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.Objects;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {

    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private TokenService tokenService;
    private EmailService emailService;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, TokenService tokenService, EmailService emailService) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    @Override
    public boolean addUser(Register register) {
        User user = new User();
        BeanUtils.copyProperties(register, user);
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        user.setCreateDate(new Date());
        return userMapper.insertSelective(user) == 1;
    }

    @Override
    public boolean isEmailExists(String email) {
        return Objects.nonNull(userMapper.findByEmail(email));
    }

    @Override
    public boolean isEmailVerified(String email) {
        return userMapper.findByEmail(email).getIsEmailVerify();
    }

}
