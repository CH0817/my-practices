package com.rex.my.web.controller;

import com.rex.my.web.controller.base.BaseControllerTest;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RegisterControllerTest extends BaseControllerTest {

    private Map<String, String[]> params;

    @Before
    public void setUp() {
        params = new HashMap<>();
        params.put("email", new String[]{"1@g.c"});
        params.put("password", new String[]{"11111111"});
        params.put("confirmPassword", new String[]{"11111111"});
    }

    @Test
    public void toRegisterPage() throws Exception {
        sendGetRequest("/register")
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    public void register() throws Exception {
        sendPostRequest("/register", params)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));
    }

    @Test
    public void registerHasError() throws Exception {
        params.put("email", new String[]{"test@email.com"});
        sendPostRequest("/register", params)
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("message"))
                .andExpect(view().name("redirect:/register"));
    }

}
