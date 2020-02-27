package com.rex.practice.web.controller.security.config;

import com.rex.practice.security.CustomAuthenticationProvider;
import com.rex.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class MockCustomAuthenticationProvider {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MockCustomAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(userService, passwordEncoder);
    }

}
