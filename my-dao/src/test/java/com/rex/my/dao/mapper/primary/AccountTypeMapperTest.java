package com.rex.my.dao.mapper.primary;

import com.rex.my.dao.mapper.primary.base.BaseMapperTest;
import com.rex.my.model.dao.primary.AccountType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Objects;

import static org.junit.Assert.*;

public class AccountTypeMapperTest extends BaseMapperTest {

    @Autowired
    private AccountTypeMapper mapper;

    @Test
    public void insertSelective() {
        Date today = new Date();
        AccountType entity = new AccountType();
        entity.setName("測試");
        entity.setRemoved(Boolean.TRUE);
        entity.setCreateDate(today);
        entity.setUpdateDate(today);
        entity.setUserId("a");
        int executeCount = mapper.insertSelective(entity);
        logger.info("id: {}", entity.getId());
        assertFalse(StringUtils.isEmpty(entity.getId()));
        assertEquals(32, entity.getId().length());
        assertEquals(1, executeCount);
    }

    @Test
    public void selectAll() {
        assertEquals(1, mapper.selectAll().size());
    }

    @Test
    public void selectByPrimaryKey() {
        assertEquals("a", mapper.selectByPrimaryKey("a").getId());
    }

    @Test
    public void update2DeleteByPrimaryKey() {
        assertEquals(1, mapper.update2DeleteByPrimaryKey("a"));
    }

    @Test
    public void updateSelectiveByPrimaryKey() {
        AccountType entity = mapper.selectByPrimaryKey("a");
        entity.setUpdateDate(new Date());
        assertEquals(1, mapper.updateSelectiveByPrimaryKey(entity));
    }

}
