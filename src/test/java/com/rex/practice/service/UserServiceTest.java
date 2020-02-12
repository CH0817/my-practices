package com.rex.practice.service;

import com.rex.practice.dao.model.User;
import com.rex.practice.model.input.Register;
import com.rex.practice.service.base.BaseServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import javax.mail.MessagingException;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest extends BaseServiceTest {

    @Autowired
    private UserService service;
    @SpyBean
    private TokenService tokenService;
    @MockBean
    private EmailService emailService;

    @Test
    public void addUser() throws MessagingException {
        String email = "1@1.c";

        when(userMapper.insertSelective(any(User.class))).thenReturn(1);
        doReturn(UUID.randomUUID().toString()).when(tokenService).createRegisterToken(email);

        Register register = new Register();
        register.setEmail(email);
        register.setPassword("11111111");

        assertTrue(service.addUser(register));

        verify(userMapper, times(1)).insertSelective(any(User.class));
        verify(tokenService, times(1)).createRegisterToken(email);
        verify(emailService, times(1)).sendConfirmRegisterEmail(email);
    }

    @Test
    public void isUserExists() {
        String email = "1@1.c";
        when(userMapper.findByEmail(email)).thenReturn(new User());
        assertTrue(service.isUserExists(email));
    }

    @Test
    public void isUserNotExists() {
        String email = "1@1.c";
        when(userMapper.findByEmail(email)).thenReturn(null);
        assertFalse(service.isUserExists(email));
    }

}
