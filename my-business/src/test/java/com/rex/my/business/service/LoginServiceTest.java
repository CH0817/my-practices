package com.rex.my.business.service;

import com.rex.my.business.service.base.BaseServiceTest;
import com.rex.my.dao.mapper.primary.UserMapper;
import com.rex.my.model.dao.primary.User;
import com.rex.my.model.vo.Login;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class LoginServiceTest extends BaseServiceTest {

    @Autowired
    private LoginService service;
    @MockBean
    private UserMapper mapper;

    @Test
    public void login() {
        User user = new User();
        user.setId("a");
        user.setEmail("test@mail.com");
        user.setPassword("11111111");
        when(mapper.findByEmail(anyString())).thenReturn(user);
        Login input = new Login();
        input.setEmail("test@mail.com");
        input.setPassword("11111111");
        assertTrue(service.login(input));
    }

}
