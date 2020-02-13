package com.rex.practice.service;

import com.rex.practice.model.input.Register;

public interface UserService {

    boolean addUser(Register register);

    boolean isEmailExists(String email);

}
