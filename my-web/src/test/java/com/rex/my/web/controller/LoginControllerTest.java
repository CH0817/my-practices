package com.rex.my.web.controller;

import com.rex.my.model.vo.Login;
import com.rex.my.web.controller.base.BaseControllerTest;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LoginControllerTest extends BaseControllerTest {

    @Test
    public void login() throws Exception {
        when(service.login(any(Login.class))).thenReturn(true);
        mvc.perform(generateLoginRequest("test@mail.com", "11111111"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("page/main"));
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