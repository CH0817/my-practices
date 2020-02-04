package com.rex.practice.dao.mapper.secondary;

import com.rex.practice.PracticeApplication;
import com.rex.practice.dao.model.secondary.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PracticeApplication.class})
@Transactional
public class CustomerMapperTest {

    @Autowired
    private CustomerMapper mapper;

    @Test
    public void insert() {
        Customer entity = new Customer();
        entity.setId("id");
        entity.setEmail("test@mail.com");
        assertEquals(1, mapper.insert(entity));
    }

}
