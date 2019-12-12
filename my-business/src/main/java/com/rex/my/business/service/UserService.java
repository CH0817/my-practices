package com.rex.my.business.service;

import com.rex.my.model.dao.primary.User;

public interface UserService {

    User findByEmail(String email);

}
