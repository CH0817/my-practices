package com.rex.my.dao.mapper.secondary;

import com.rex.my.dao.config.DaoConfig;
import com.rex.my.model.dao.secondary.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DaoConfig.class)
@ActiveProfiles("test")
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
