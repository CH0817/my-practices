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
import static org.mockito.Mockito.*;

public class TokenServiceTest extends BaseServiceTest {

    @Autowired
    private TokenService service;

    @Test
    public void createRegisterToken() {
        when(registerTokenMapper.insertSelective(any(RegisterToken.class))).thenReturn(1);
        assertTrue(StringUtils.isNotBlank(service.createRegisterToken(userId)));
    }

    @Test
    public void getRegisterToken() {
        RegisterToken registerToken = new RegisterToken();
        registerToken.setUserId(userId);
        registerToken.setToken(UUID.randomUUID().toString());
        when(registerTokenMapper.findByUserId(eq(userId))).thenReturn(registerToken);
        assertEquals(registerToken.getToken(), service.getRegisterToken(userId));
    }

    @Test
    public void isTokenExpired() throws Exception {
        RegisterToken token = new RegisterToken();
        token.setExpireDate(Date.from(LocalDate.now().minusDays(1L).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(registerTokenMapper.findByUserId(eq(userId))).thenReturn(token);
        assertTrue(service.isTokenExpired(userId));
    }

    @Test(expected = Exception.class)
    public void isTokenExpiredEmailNotFound() throws Exception {
        when(registerTokenMapper.findByUserId(eq(userId))).thenReturn(null);
        assertTrue(service.isTokenExpired(userId));
    }

    @Test
    public void deleteTokenEmptyUserId() {
        service.deleteToken("");
        verify(registerTokenMapper, never()).deleteByUserId(userId);
    }

    @Test
    public void deleteTokenNullUserId() {
        service.deleteToken(null);
        verify(registerTokenMapper, never()).deleteByUserId(userId);
    }

    @Test
    public void deleteToken() {
        service.deleteToken(userId);
        verify(registerTokenMapper, times(1)).deleteByUserId(userId);
    }

}
