package com.rex.my.business.service;

import com.rex.my.business.service.base.BaseServiceTest;
import com.rex.my.model.dao.primary.User;
import com.rex.my.model.input.Register;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class UserServiceTest extends BaseServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void addUser() {
        Register register = new Register();
        register.setEmail("1@1.c");
        register.setPassword("11111111");
        service.addUser(register);
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
