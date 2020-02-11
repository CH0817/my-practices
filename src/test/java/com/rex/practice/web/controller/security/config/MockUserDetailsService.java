package com.rex.practice.web.controller.security.config;

import com.rex.practice.dao.mapper.UserMapper;
import com.rex.practice.dao.model.User;
import com.rex.practice.model.security.CustomUserDetailsServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.mockito.Mockito.when;

@TestConfiguration
public class MockUserDetailsService {

    @MockBean
    private UserMapper userMapper;

    @Bean
    public UserDetailsService userDetailsService() {
        User user = new User();
        user.setId("a");
        user.setEmail("test@email.com");
        user.setPassword("{bcrypt}$2a$10$wq1FE6qSyJGi/vhQ4/b82uU.6n.g6b6mhLChG9Xb8K5rcKgLyeZcq");

        when(userMapper.findByEmail("test@email.com")).thenReturn(user);

        return new CustomUserDetailsServiceImpl(userMapper);
    }

}
