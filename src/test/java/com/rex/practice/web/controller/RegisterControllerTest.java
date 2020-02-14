package com.rex.practice.web.controller;

import com.rex.practice.model.input.Register;
import com.rex.practice.web.controller.base.BaseControllerTest;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.anyString;
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
        when(userService.addUser(any(Register.class))).thenReturn(true);
        when(tokenService.createRegisterToken(anyString())).thenReturn(UUID.randomUUID().toString().replace("-", ""));

        sendPostRequest("/register", params)
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("message", nullValue()))
                .andExpect(view().name("redirect:/login"));

        verify(userService, times(1)).addUser(any(Register.class));
        verify(tokenService, times(1)).createRegisterToken(anyString());
        verify(emailService, times(1)).sendConfirmRegisterEmail(anyString(), anyString());
    }

    @Test
    public void emailIsEmpty() throws Exception {
        params.put("email", new String[]{""});
        verifyError("Email不能為空", "redirect:/register");
    }

    @Test
    public void emailFormatError() throws Exception {
        params.put("email", new String[]{"111"});
        verifyError("錯誤的Email格式", "redirect:/register");
    }

    @Test
    public void passwordIsEmpty() throws Exception {
        params.put("password", new String[]{""});
        verifyError("密碼不能為空，密碼必須是8~12碼", "redirect:/register");
    }

    @Test
    public void passwordFormatError() throws Exception {
        params.put("password", new String[]{"111"});
        verifyError("密碼必須是8~12碼", "redirect:/register");
    }

    @Test
    public void confirmPasswordIsEmpty() throws Exception {
        params.put("confirmPassword", new String[]{""});
        verifyError("確認密碼不能為空", "redirect:/register");
    }

    @Test
    public void passwordDifferent() throws Exception {
        params.put("confirmPassword", new String[]{"22222222"});
        verifyError("兩次密碼不相同", "redirect:/register");
    }

    @Test
    public void emailRegistered() throws Exception {
        when(userService.isEmailExists(anyString())).thenReturn(true);
        when(userService.isEmailVerified(anyString())).thenReturn(true);
        verifyError("Email已被註冊", "redirect:/register");
        verify(userService, times(1)).isEmailExists(anyString());
    }

    @Test
    public void emailVerifying() throws Exception {
        when(userService.isEmailExists(anyString())).thenReturn(true);
        when(userService.isEmailVerified(anyString())).thenReturn(false);
        verifyError("Email驗證中", "redirect:/login");
        verify(userService, times(1)).isEmailExists(anyString());
        verify(userService, times(1)).isEmailVerified(anyString());
    }


    private void verifyError(String expectMessage, String viewName) throws Exception {
        sendPostRequest("/register", params)
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("message", expectMessage))
                .andExpect(view().name(viewName));
    }

}
