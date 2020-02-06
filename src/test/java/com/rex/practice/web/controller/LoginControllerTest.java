package com.rex.practice.web.controller;

import com.rex.practice.web.controller.base.BaseControllerTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

// TODO 怎麼測試 security login
@Ignore
public class LoginControllerTest extends BaseControllerTest {

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