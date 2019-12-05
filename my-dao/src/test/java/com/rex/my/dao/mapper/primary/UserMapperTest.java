package com.rex.my.dao.mapper.primary;

import com.rex.my.model.dao.primary.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserMapperTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserMapper mapper;

    @Test
    public void insert() {
        User user = new User();
        user.setEmail("mail@com.tw");
        user.setPassword("11111111");
        user.setCreateDate(new Date());
        int executeCount = mapper.insert(user);
        logger.info("id: {}", user.getId());
        assertFalse(StringUtils.isEmpty(user.getId()));
        assertEquals(32, user.getId().length());
        assertEquals(1, executeCount);
    }

    @Test
    public void selectAll() {
        List<User> users = mapper.selectAll();
        assertEquals(1, users.size());
    }

    @Test
    public void selectByPrimaryKey() {
        User entity = mapper.selectByPrimaryKey("a");
        assertEquals("a", entity.getId());
    }

    @Test
    public void deleteByPrimaryKey() {
        int executeCount = mapper.deleteByPrimaryKey("a");
        assertEquals(1, executeCount);
    }

    @Test
    public void updateByPrimaryKey() {
        User entity = mapper.selectByPrimaryKey("a");
        int executeCount = mapper.updateByPrimaryKey(entity);
        assertEquals(1, executeCount);
    }

    @Test
    public void findByEmail(){
        assertTrue(Objects.nonNull(mapper.findByEmail("test@email.com")));
    }

}
