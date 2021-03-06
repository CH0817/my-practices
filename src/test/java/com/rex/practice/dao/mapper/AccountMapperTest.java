package com.rex.practice.dao.mapper;

import com.rex.practice.dao.mapper.base.BaseMapperTest;
import com.rex.practice.dao.model.Account;
import com.rex.practice.model.easyui.grid.AccountGridVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@Sql({"/db/data/data-dev-user.sql", "/db/data/data-dev-account_type.sql", "/db/data/data-dev-account.sql"})
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
        assertEquals(5, mapper.selectAll("a").size());
    }

    @Test
    public void selectByPrimaryKey() {
        assertEquals("a", mapper.selectByPrimaryKey("a").getId());
    }

    @Test
    public void updateByPrimaryKey() {
        Account entity = mapper.selectByPrimaryKey("a");
        entity.setUpdateDate(new Date());
        int executeCount = mapper.updateSelectiveByPrimaryKey(entity);
        assertEquals(1, executeCount);
    }

    @Test
    public void selectForGrid() {
        List<AccountGridVo> entities = mapper.selectForGrid("a");
        assertEquals(5, entities.size());
        assertEquals("玉山", entities.get(0).getName());
        assertEquals(0, new BigDecimal(1000).compareTo(entities.get(0).getMoney()));
        assertEquals("b", entities.get(0).getAccount_type_id());
    }

    @Test
    public void updateToDeleteByIds() {
        assertEquals(2, mapper.updateToDeleteByIds(new String[]{"a", "b"}, "a"));
    }

}
