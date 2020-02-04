package com.rex.practice.service;

import com.rex.practice.model.input.Register;

public interface UserService {

    void addUser(Register register);

    boolean isUserExists(String email);

}
