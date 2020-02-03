package com.rex.my.business.service;

import com.rex.my.business.service.base.BaseServiceTest;
import com.rex.my.model.dao.primary.User;
import com.rex.my.model.input.Register;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class RegisterServiceTest extends BaseServiceTest {

    @Autowired
    private RegisterService service;

    private Register register;
    private BindingResult bindingResult;

    @Before
    public void setUp() {
        register = new Register();
        register.setEmail("1@g.c");
        register.setPassword("11111111");
        register.setConfirmPassword("11111111");

        bindingResult = new DataBinder(register).getBindingResult();
    }

    @Test
    public void verifySuccess() {
        assertTrue(service.verify(register, bindingResult).isEmpty());
    }

    @Test
    public void fieldNotVerify() {
        bindingResult.addError(new FieldError("", "email", "Email不能為空"));
        Optional<String> verify = service.verify(register, bindingResult);
        assertTrue(verify.isPresent());
        assertEquals("Email不能為空", verify.get());
    }

    @Test
    public void passwordNotVerify() {
        register.setPassword("22222222");
        Optional<String> verify = service.verify(register, bindingResult);
        assertTrue(verify.isPresent());
        assertEquals("二次密碼不相同", verify.get());
    }

    @Test
    public void emailAlreadyRegistered() {
        when(userMapper.findByEmail(anyString())).thenReturn(new User());
        Optional<String> verify = service.verify(register, bindingResult);
        assertTrue(verify.isPresent());
        assertEquals("Email已被註冊", verify.get());
    }

}
