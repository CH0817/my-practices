package com.rex.my.web.controller.base;

import com.rex.MyWebApplication;
import com.rex.my.business.service.LoginService;
import com.rex.my.model.dao.primary.User;
import com.rex.my.web.constant.SessionAttribute;
import org.junit.Before;
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
import org.springframework.mock.web.MockHttpSession;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MyWebApplication.class})
@AutoConfigureMockMvc
@Ignore
public abstract class BaseControllerTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected MockHttpSession session;
    @MockBean
    protected LoginService service;

    @Before
    public void setUserSession() {
        User user = new User();
        user.setId("a");
        user.setEmail("test@email.com");
        session.setAttribute(SessionAttribute.USER,user);
    }

}
