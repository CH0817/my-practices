package com.rex.practice.service.base;

import com.rex.practice.config.BeansConfig;
import com.rex.practice.dao.mapper.*;
import com.rex.practice.service.impl.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
// TODO 這邊如何減少 class 的宣告？
@ContextConfiguration(classes = {AccountServiceImpl.class, ComboboxServiceImpl.class, ItemServiceImpl.class,
        LoginServiceImpl.class, MenuServiceImpl.class, RegisterServiceImpl.class, TradeServiceImpl.class,
        UserServiceImpl.class, TokenServiceImpl.class, EmailServiceImpl.class})
@MockBean(classes = {AccountTypeMapper.class})
@Import({BeansConfig.class})
@TestPropertySource(locations = {"classpath:test.properties"})
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
    @MockBean
    protected RegisterTokenMapper registerTokenMapper;

}
