package com.rex.practice.dao.mapper;

import com.rex.practice.dao.mapper.base.BaseMapperTest;
import com.rex.practice.dao.model.RegisterToken;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class RegisterTokenMapperTest extends BaseMapperTest {

    @Autowired
    private RegisterTokenMapper mapper;

    @Test
    public void insertSelective() {
        RegisterToken entity = new RegisterToken();
        entity.setEmail("test@mail.com");
        entity.setToken(UUID.randomUUID().toString().replace("-", ""));
        assertEquals(1, mapper.insertSelective(entity));
    }

}
