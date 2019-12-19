package com.rex.my.web.controller;

import com.rex.MyWebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MyWebApplication.class})
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void login() throws Exception {
        mvc.perform(getFormLogin("11111111")).andDo(print()).andExpect(authenticated());
    }

    @Test
    public void invalidLogin() throws Exception {
        mvc.perform(getFormLogin("1")).andDo(print()).andExpect(unauthenticated());
    }

    private RequestBuilder getFormLogin(String password) {
        return formLogin().userParameter("email").user("test@email.com").password(password);
    }

}