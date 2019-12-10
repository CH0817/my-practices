package com.rex.my.dao.mapper.primary;

import com.rex.my.dao.mapper.primary.base.BaseMapperTest;
import com.rex.my.model.dao.primary.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Objects;

import static org.junit.Assert.*;

public class UserMapperTest extends BaseMapperTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserMapper mapper;

    @Test
    public void insertSelective() {
        Date today = new Date();
        User user = new User();
        user.setEmail("mail@com.tw");
        user.setPassword("11111111");
        user.setRemoved(Boolean.TRUE);
        user.setCreateDate(today);
        user.setUpdateDate(today);
        int executeCount = mapper.insertSelective(user);
        logger.info("id: {}", user.getId());
        assertFalse(StringUtils.isEmpty(user.getId()));
        assertEquals(32, user.getId().length());
        assertEquals(1, executeCount);
    }

    @Test
    public void selectAll() {
        assertEquals(3, mapper.selectAll().size());
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
        assertEquals(1, mapper.updateSelectiveByPrimaryKey(mapper.selectByPrimaryKey("a")));
    }

    @Test
    public void findByEmail() {
        assertTrue(Objects.nonNull(mapper.findByEmail("test@email.com")));
    }

}
