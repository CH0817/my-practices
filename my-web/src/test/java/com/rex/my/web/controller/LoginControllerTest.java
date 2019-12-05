package com.rex.my.web.controller;

import com.rex.MyWebApplication;
import com.rex.my.business.service.LoginService;
import com.rex.my.model.vo.Login;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MyWebApplication.class})
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private LoginService service;

    @Test
    public void login() throws Exception {
        when(service.login(any(Login.class))).thenReturn(true);
        mvc.perform(post("/login")
                .param("email", "t@mail.com")
                .param("password", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("main"));
    }

}