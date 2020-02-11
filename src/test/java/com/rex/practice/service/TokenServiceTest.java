package com.rex.practice.service;

import com.rex.practice.dao.model.RegisterToken;
import com.rex.practice.service.base.BaseServiceTest;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TokenServiceTest extends BaseServiceTest {

    @Autowired
    private TokenService service;

    @Test
    public void createRegisterToken() {
        when(registerTokenMapper.insertSelective(any(RegisterToken.class))).thenReturn(1);
        assertTrue(StringUtils.isNotBlank(service.createRegisterToken("1@1.c")));
    }

}
