package com.rex.my.business.service;

import com.rex.my.model.input.Register;

public interface UserService {

    void addUser(Register register);

    boolean isUserExists(String email);

}
