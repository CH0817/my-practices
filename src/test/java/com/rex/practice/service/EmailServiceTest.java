package com.rex.practice.service;

import com.rex.practice.service.base.BaseServiceTest;
import com.rex.practice.service.impl.EmailServiceImpl;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.UUID;

@ContextConfiguration(classes = {EmailServiceImpl.class})
public class EmailServiceTest extends BaseServiceTest {

    @Autowired
    private EmailService service;

    @Test
    @Ignore
    public void sendConfirmRegisterEmail() {
        service.sendConfirmRegisterEmail(userId, registerEmail, UUID.randomUUID().toString().replace("-", ""));
    }

}
