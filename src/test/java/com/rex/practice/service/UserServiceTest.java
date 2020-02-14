package com.rex.practice.service;

import com.rex.practice.dao.model.User;
import com.rex.practice.model.input.Register;
import com.rex.practice.service.base.BaseServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest extends BaseServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void addUser() {
        when(userMapper.insertSelective(any(User.class))).thenReturn(1);

        Register register = new Register();
        register.setEmail("1@1.c");
        register.setPassword("11111111");

        assertTrue(service.addUser(register));

        verify(userMapper, times(1)).insertSelective(any(User.class));
    }

    @Test
    public void isUserExists() {
        String email = "1@1.c";
        when(userMapper.findByEmail(email)).thenReturn(new User());
        assertTrue(service.isEmailExists(email));
    }

    @Test
    public void isUserNotExists() {
        String email = "1@1.c";
        when(userMapper.findByEmail(email)).thenReturn(null);
        assertFalse(service.isEmailExists(email));
    }

}
