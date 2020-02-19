package com.rex.practice.service;

import com.rex.practice.dao.model.User;
import com.rex.practice.model.input.Register;

import java.util.Optional;

public interface UserService {

    boolean addUser(Register register);

    boolean isEmailExists(String email);

    boolean isEmailVerified(String email);

    boolean updateEmailVerifyStatus(String userId);

    Optional<User> findByEmail(String email);

    Optional<User> findById(String userId);

}
