package com.rex.practice.service;

import com.rex.practice.dao.model.User;
import com.rex.practice.model.vo.Login;
import com.rex.practice.service.base.BaseServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class LoginServiceTest extends BaseServiceTest {

    @Autowired
    private LoginService service;

    @Test
    public void login() {
        User user = new User();
        user.setId("a");
        user.setEmail("test@mail.com");
        user.setPassword("11111111");
        when(userMapper.findByEmail(anyString())).thenReturn(user);
        Login input = new Login();
        input.setEmail("test@mail.com");
        input.setPassword("11111111");
        assertTrue(service.login(input));
    }

}
