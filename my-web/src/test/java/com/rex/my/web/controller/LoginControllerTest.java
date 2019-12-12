package com.rex.my.web.controller;

import com.rex.my.model.vo.Login;
import com.rex.my.web.controller.base.BaseControllerTest;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LoginControllerTest extends BaseControllerTest {

    private String url;
    private Map<String, String> paramMap;

    @Before
    public void setParamMap() {
        paramMap = new HashMap<>();
        paramMap.put("email", "test@email.com");
        paramMap.put("password", "11111111");
    }

    @Before
    public void setUrl() {
        url = "/login";
    }

    @Test
    public void login() throws Exception {
        when(service.login(any(Login.class))).thenReturn(true);
        sendPostRequest(url, paramMap)
                .andExpect(status().isOk())
                .andExpect(view().name("page/main"));
    }

    @Test
    public void loginNoEmailAndPassword() throws Exception {
        paramMap.put("email", "");
        paramMap.put("password", "");
        sendPostRequest(url, paramMap)
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("message", "Email不能為空、密碼長度必須為8~12"));
    }

    @Test
    public void loginWrongEmailFormat() throws Exception {
        paramMap.put("email", "test");
        sendPostRequest(url, paramMap)
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("message", "Email格式錯誤"));
    }

    @Test
    public void loginWrongPasswordFormat() throws Exception {
        paramMap.put("password", "1111");
        sendPostRequest(url, paramMap)
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("message", "密碼長度必須為8~12"));
    }

}