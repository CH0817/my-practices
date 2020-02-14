package com.rex.practice.web.controller;

import com.rex.practice.model.input.Register;
import com.rex.practice.model.verify.RegisterError;
import com.rex.practice.web.controller.base.BaseControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.nullValue;
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
                .andExpect(flash().attribute("message", nullValue()))
                .andExpect(view().name("redirect:/login"));

        verify(registerService, times(1)).register(any(Register.class));
    }

    @Test
    public void emailIsEmpty() throws Exception {
        verifyError("Email不能為空", "redirect:/register");
    }

    @Test
    public void emailFormatError() throws Exception {
        verifyError("錯誤的Email格式", "redirect:/register");
    }

    @Test
    public void passwordIsEmpty() throws Exception {
        verifyError("密碼不能為空，密碼必須是8~12碼", "redirect:/register");
    }

    @Test
    public void passwordFormatError() throws Exception {
        verifyError("密碼必須是8~12碼", "redirect:/register");
    }

    @Test
    public void confirmPasswordIsEmpty() throws Exception {
        verifyError("確認密碼不能為空", "redirect:/register");
    }

    @Test
    public void passwordDifferent() throws Exception {
        verifyError("兩次密碼不相同", "redirect:/register");
    }

    @Test
    public void emailRegistered() throws Exception {
        verifyError("Email已被註冊", "redirect:/register");
    }

    @Test
    public void emailVerifying() throws Exception {
        verifyError("Email驗證中", "redirect:/login");
    }

    private void verifyError(String errorMessage, String viewName) throws Exception {
        Optional<RegisterError> optional = Optional.of(new RegisterError(errorMessage, viewName));

        when(registerService.verify(any(Register.class), any(BindingResult.class))).thenReturn(optional);

        sendPostRequest("/register", params)
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("message", optional.get().getErrorMessage()))
                .andExpect(view().name(optional.get().getViewName()));

        verify(registerService, times(1)).verify(any(Register.class), any(BindingResult.class));
    }

}
