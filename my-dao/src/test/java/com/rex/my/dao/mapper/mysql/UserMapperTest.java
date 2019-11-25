package com.rex.my.dao.mapper.mysql;

import com.rex.my.entity.mysql.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserMapperTest {

    @Autowired
    private UserMapper mapper;

    @Test
    public void insertTest() {
        User user = new User();
        user.setEmail("mail@com.tw");

        System.out.println(mapper.insert(user));
    }

}
