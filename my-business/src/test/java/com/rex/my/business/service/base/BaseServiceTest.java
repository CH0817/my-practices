package com.rex.my.business.service.base;

import com.rex.MyBusinessApplication;
import com.rex.my.dao.mapper.primary.AccountMapper;
import com.rex.my.dao.mapper.primary.FunctionMapper;
import com.rex.my.dao.mapper.primary.ItemMapper;
import com.rex.my.dao.mapper.primary.UserMapper;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MyBusinessApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Ignore
public abstract class BaseServiceTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    @MockBean
    protected UserMapper userMapper;
    @MockBean
    protected ItemMapper itemMapper;
    @MockBean
    protected AccountMapper accountMapper;
    @MockBean
    protected FunctionMapper functionMapper;

}
