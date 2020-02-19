package com.rex.practice.service.impl;

import com.rex.practice.dao.mapper.UserMapper;
import com.rex.practice.dao.model.User;
import com.rex.practice.model.input.Register;
import com.rex.practice.service.UserService;
import com.rex.practice.service.base.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {

    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
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

    @Override
    public boolean updateEmailVerifyStatus(String userId) {
        Optional<User> optional = Optional.ofNullable(userMapper.selectByPrimaryKey(userId));
        if (optional.isPresent()) {
            User entity = optional.get();
            entity.setIsEmailVerify(true);
            entity.setUpdateDate(new Date());
            return userMapper.updateSelectiveByPrimaryKey(entity) == 1;
        }
        return false;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userMapper.findByEmail(email));
    }

    @Override
    public Optional<User> findById(String userId) {
        return Optional.ofNullable(userMapper.selectByPrimaryKey(userId));
    }

}
