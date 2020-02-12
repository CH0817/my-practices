package com.rex.practice.service.impl;

import com.rex.practice.dao.mapper.UserMapper;
import com.rex.practice.dao.model.User;
import com.rex.practice.model.input.Register;
import com.rex.practice.service.EmailService;
import com.rex.practice.service.TokenService;
import com.rex.practice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.Objects;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(getClass());

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
        boolean result = userMapper.insertSelective(user) == 1;
        if (result) {
            tokenService.createRegisterToken(register.getEmail());
            try {
                emailService.sendConfirmRegisterEmail(register.getEmail());
            } catch (MessagingException e) {
                logger.error("send register verify email error", e);
            }
        }
        return result;
    }

    @Override
    public boolean isUserExists(String email) {
        return Objects.nonNull(userMapper.findByEmail(email));
    }

}
