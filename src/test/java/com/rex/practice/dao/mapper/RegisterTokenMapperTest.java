package com.rex.practice.dao.mapper;

import com.rex.practice.dao.mapper.base.BaseMapperTest;
import com.rex.practice.dao.model.RegisterToken;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Sql({"/db/data/data-dev-user.sql", "/db/data/data-dev-register_token.sql"})
public class RegisterTokenMapperTest extends BaseMapperTest {

    @Autowired
    private RegisterTokenMapper mapper;

    @Test
    public void insertSelective() {
        RegisterToken entity = new RegisterToken();
        entity.setUserId("b");
        entity.setToken(UUID.randomUUID().toString().replace("-", ""));
        entity.setExpireDate(Date.from(LocalDate.now().plusDays(7L).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        assertEquals(1, mapper.insertSelective(entity));
    }

    @Test
    public void findByUserId() {
        assertTrue(Objects.nonNull(mapper.findByUserId("a")));
    }

    @Test
    public void deleteByUserId() {
        assertEquals(1, mapper.deleteByUserId("a"));
    }

}
