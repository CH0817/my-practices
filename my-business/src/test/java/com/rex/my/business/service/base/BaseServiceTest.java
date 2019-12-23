package com.rex.my.business.service.base;

import com.rex.my.business.config.BusinessConfig;
import com.rex.my.dao.mapper.primary.AccountMapper;
import com.rex.my.dao.mapper.primary.FunctionMapper;
import com.rex.my.dao.mapper.primary.ItemMapper;
import com.rex.my.dao.mapper.primary.UserMapper;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BusinessConfig.class})
@Ignore
public abstract class BaseServiceTest {

    @MockBean
    protected UserMapper userMapper;
    @MockBean
    protected ItemMapper itemMapper;
    @MockBean
    protected AccountMapper accountMapper;
    @MockBean
    protected FunctionMapper functionMapper;

}
