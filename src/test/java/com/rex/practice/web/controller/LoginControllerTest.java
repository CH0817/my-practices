package com.rex.practice.web.controller;

import com.rex.practice.web.controller.base.BaseControllerTest;
import com.rex.practice.web.controller.security.config.MockUserDetailsService;
import org.junit.Test;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({MockUserDetailsService.class})
public class LoginControllerTest extends BaseControllerTest {


    @Test
    public void login() throws Exception {
        mvc.perform(getLoginForm("test@email.com"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/main"))
                .andExpect(authenticated());
    }

    @Test
    public void invalidLogin() throws Exception {
        mvc.perform(getLoginForm("test_02@email.com"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/login-error"))
                .andExpect(unauthenticated());
    }

    private RequestBuilder getLoginForm(String email) {
        return formLogin().userParameter("email").user(email).password("11111111");
    }

}