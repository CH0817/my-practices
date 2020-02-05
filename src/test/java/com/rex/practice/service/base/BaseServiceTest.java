package com.rex.practice.service.base;

import com.rex.practice.PracticeApplication;
import com.rex.practice.dao.mapper.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PracticeApplication.class})
@Transactional
@Ignore
public abstract class BaseServiceTest {

    protected String userId = "a";
    @MockBean
    protected UserMapper userMapper;
    @MockBean
    protected ItemMapper itemMapper;
    @MockBean
    protected AccountMapper accountMapper;
    @MockBean
    protected FunctionMapper functionMapper;
    @MockBean
    protected TradeMapper tradeMapper;

}
