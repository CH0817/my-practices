package com.rex.my.business.service.impl;

import com.rex.my.business.service.UserService;
import com.rex.my.dao.mapper.primary.UserMapper;
import com.rex.my.model.dao.primary.User;
import com.rex.my.model.input.Register;
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

    @Autowired
    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addUser(Register register) {
        User user = new User();
        BeanUtils.copyProperties(register, user);
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        user.setCreateDate(new Date());
        userMapper.insertSelective(user);
    }

    @Override
    public boolean isUserExists(String email) {
        return Objects.nonNull(userMapper.findByEmail(email));
    }

}
