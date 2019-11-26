package com.rex.my.dao.mapper.mysql;

import com.rex.my.dao.entity.mysql.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static org.junit.Assert.assertTrue;

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
        mapper.insert(user);
        logger.info("id: {}", user.getId());
        assertTrue(!StringUtils.isEmpty(user.getId()) && user.getId().length() == 36);
    }

}
