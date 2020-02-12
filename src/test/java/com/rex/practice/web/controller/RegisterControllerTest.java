package com.rex.practice.web.controller;

import com.rex.practice.model.input.Register;
import com.rex.practice.web.controller.base.BaseControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
        verify(registerService, times(1)).register(any(Register.class));
    }

    @Test
    public void registerHasError() throws Exception {
        Optional<String> verifyOptional = Optional.of("Email已被註冊");
        when(registerService.verify(any(Register.class), any(BindingResult.class))).thenReturn(verifyOptional);
        params.put("email", new String[]{"test@email.com"});
        sendPostRequest("/register", params)
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("message", verifyOptional.get()))
                .andExpect(view().name("redirect:/register"));
    }

}
