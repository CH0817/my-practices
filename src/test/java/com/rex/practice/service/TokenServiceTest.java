package com.rex.practice.service;

import com.rex.practice.dao.model.RegisterToken;
import com.rex.practice.service.base.BaseServiceTest;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class TokenServiceTest extends BaseServiceTest {

    private final String EMAIL = "1@1.c";
    @Autowired
    private TokenService service;

    @Test
    public void createRegisterToken() {
        when(registerTokenMapper.insertSelective(any(RegisterToken.class))).thenReturn(1);
        assertTrue(StringUtils.isNotBlank(service.createRegisterToken(EMAIL)));
    }

    @Test
    public void getRegisterToken() {
        RegisterToken registerToken = new RegisterToken();
        registerToken.setEmail(EMAIL);
        registerToken.setToken(UUID.randomUUID().toString());
        when(registerTokenMapper.findByEmail(eq(EMAIL))).thenReturn(registerToken);
        assertEquals(registerToken.getToken(), service.getRegisterToken(EMAIL));
    }

    @Test
    public void isTokenExpired() throws Exception {
        RegisterToken token = new RegisterToken();
        token.setExpireDate(Date.from(LocalDate.now().minusDays(1L).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(registerTokenMapper.findByEmail(eq(EMAIL))).thenReturn(token);
        assertTrue(service.isTokenExpired(EMAIL));
    }

    @Test(expected = Exception.class)
    public void isTokenExpiredEmailNotFound() throws Exception {
        when(registerTokenMapper.findByEmail(eq(EMAIL))).thenReturn(null);
        assertTrue(service.isTokenExpired(EMAIL));
    }

}
