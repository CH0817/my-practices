package com.rex.my.web.controller.base;

import com.rex.MyWebApplication;
import com.rex.my.business.service.LoginService;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MyWebApplication.class})
@AutoConfigureMockMvc
@Ignore
public abstract class BaseControllerTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    protected MockMvc mvc;
    @MockBean
    protected LoginService service;

}
