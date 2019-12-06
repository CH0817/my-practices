package com.rex.my.dao.mapper.primary;

import com.rex.my.dao.mapper.primary.base.BaseMapperTest;
import com.rex.my.model.dao.primary.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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

    // @Test
    // public void selectAll() {
    //     List<User> users = mapper.selectAll();
    //     assertEquals(1, users.size());
    // }
    //
    // @Test
    // public void selectByPrimaryKey() {
    //     User entity = mapper.selectByPrimaryKey("a");
    //     assertEquals("a", entity.getId());
    // }
    //
    // @Test
    // public void deleteByPrimaryKey() {
    //     int executeCount = mapper.deleteByPrimaryKey("a");
    //     assertEquals(1, executeCount);
    // }
    //
    // @Test
    // public void updateByPrimaryKey() {
    //     User entity = mapper.selectByPrimaryKey("a");
    //     int executeCount = mapper.updateByPrimaryKey(entity);
    //     assertEquals(1, executeCount);
    // }
    //
    // @Test
    // public void findByEmail() {
    //     assertTrue(Objects.nonNull(mapper.findByEmail("test@email.com")));
    // }

}
