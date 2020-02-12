package com.rex.practice.service;

import com.rex.practice.service.base.BaseServiceTest;
import com.rex.practice.service.impl.EmailServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;

import javax.mail.MessagingException;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {EmailServiceImpl.class})
public class EmailServiceTest extends BaseServiceTest {

    @Autowired
    private EmailService service;
    @SpyBean
    private TokenService tokenService;

    @Test
    public void sendConfirmRegisterEmail() throws MessagingException {
        String email = "rexyu0817@gmail.com";
        String token = UUID.randomUUID().toString().replace("-", "");
        doReturn(token).when(tokenService).getRegisterToken(email);
        service.sendConfirmRegisterEmail(email);
        verify(tokenService, times(1)).getRegisterToken(email);
    }

}
