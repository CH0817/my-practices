package com.rex.my.api.web.controller;

import com.rex.my.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private UserService service;

    @Autowired
    public UserApiController(UserService service) {
        this.service = service;
    }

    @PostMapping(name = "/insert", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void insert() {
        System.out.println("/api/user/insert");
    }

}
