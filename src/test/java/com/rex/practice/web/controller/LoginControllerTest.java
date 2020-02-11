package com.rex.practice.web.controller;

import com.rex.practice.web.controller.base.BaseControllerTest;
import com.rex.practice.web.controller.security.config.MockUserDetailsService;
import org.junit.Test;
import org.springframework.context.annotation.Import;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Import({MockUserDetailsService.class})
public class LoginControllerTest extends BaseControllerTest {


    @Test
    public void login() throws Exception {
        Map<String, String[]> params = new HashMap<>();
        params.put("email", new String[]{"test@email.com"});
        params.put("password", new String[]{"11111111"});

        sendPostRequest("/login", params).andDo(print()).andExpect(authenticated());
    }

    @Test
    public void invalidLogin() throws Exception {
        Map<String, String[]> params = new HashMap<>();
        params.put("email", new String[]{"test_02@email.com"});
        params.put("password", new String[]{"11111111"});

        sendPostRequest("/login", params).andDo(print()).andExpect(unauthenticated());
    }

}