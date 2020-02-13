package com.rex.practice.service;

import com.rex.practice.service.base.BaseServiceTest;
import com.rex.practice.service.impl.EmailServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.UUID;

@ContextConfiguration(classes = {EmailServiceImpl.class})
public class EmailServiceTest extends BaseServiceTest {

    @Autowired
    private EmailService service;

    @Test
    public void sendConfirmRegisterEmail() {
        service.sendConfirmRegisterEmail("rexyu0817@gmail.com", UUID.randomUUID().toString().replace("-", ""));
    }

}
