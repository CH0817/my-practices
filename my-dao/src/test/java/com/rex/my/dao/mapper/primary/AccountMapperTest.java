package com.rex.my.dao.mapper.primary;

import com.rex.my.dao.mapper.primary.base.BaseMapperTest;
import com.rex.my.model.dao.primary.Account;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class AccountMapperTest extends BaseMapperTest {

    @Autowired
    private AccountMapper mapper;

    @Test
    public void insertSelective() {
        Date today = new Date();
        Account entity = new Account();
        entity.setName("測試帳號");
        entity.setUserId("a");
        entity.setAccountTypeId("a");
        entity.setMoney(new BigDecimal(100000));
        entity.setRemoved(Boolean.TRUE);
        entity.setCreateDate(today);
        entity.setUpdateDate(today);
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
    public void updateByPrimaryKey() {
        Account entity = mapper.selectByPrimaryKey("a");
        entity.setUpdateDate(new Date());
        int executeCount = mapper.updateSelectiveByPrimaryKey(entity);
        assertEquals(1, executeCount);
    }

}
