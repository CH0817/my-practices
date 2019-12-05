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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        mvc.perform(generateLoginRequest("test@mail.com", "11111111"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("main"));
    }

    @Test
    public void loginNoEmailAndPassword() throws Exception {
        mvc.perform(generateLoginRequest("", ""))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("message", "Email不能為空、密碼長度必須為8~12"));
    }

    @Test
    public void loginWrongEmailFormat() throws Exception {
        mvc.perform(generateLoginRequest("test", "11111111"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("message", "Email格式錯誤"));
    }

    @Test
    public void loginWrongPasswordFormat() throws Exception {
        mvc.perform(generateLoginRequest("test@mail.com", "1111"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("message", "密碼長度必須為8~12"));
    }

    private MockHttpServletRequestBuilder generateLoginRequest(String email, String password) {
        return post("/login")
                .param("email", email)
                .param("password", password);
    }

}