package com.rex.practice.web.controller;

import com.rex.practice.model.input.Register;
import com.rex.practice.model.verify.RegisterError;
import com.rex.practice.model.verify.RegisterVerifyError;
import com.rex.practice.web.controller.base.BaseControllerTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public void registerButEmailIsEmpty() throws Exception {
        verifyError("Email不能為空", "redirect:/register");
    }

    @Test
    public void registerButEmailFormatError() throws Exception {
        verifyError("錯誤的Email格式", "redirect:/register");
    }

    @Test
    public void registerButPasswordIsEmpty() throws Exception {
        verifyError("密碼不能為空，密碼必須是8~12碼", "redirect:/register");
    }

    @Test
    public void registerButPasswordFormatError() throws Exception {
        verifyError("密碼必須是8~12碼", "redirect:/register");
    }

    @Test
    public void registerButConfirmPasswordIsEmpty() throws Exception {
        verifyError("確認密碼不能為空", "redirect:/register");
    }

    @Test
    public void registerButPasswordDifferent() throws Exception {
        verifyError("兩次密碼不相同", "redirect:/register");
    }

    @Test
    public void registerButEmailRegistered() throws Exception {
        verifyError("Email已被註冊", "redirect:/register");
    }

    @Test
    public void registerButEmailVerifying() throws Exception {
        verifyError("Email驗證中", "redirect:/login");
    }

    @Test
    public void accountVerifyButNotRegisterData() throws Exception {
        String token = randomToken();

        RegisterVerifyError error = new RegisterVerifyError();
        error.setAccountError(true);

        when(registerService.accountVerify(userId, token)).thenReturn(Optional.of(error));

        sendRequest(get("/register/verify/{userId}/{token}", userId, token))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/register"));

        verify(registerService, times(1)).accountVerify(userId, token);
    }

    @Test
    public void accountVerifyButTokenError() throws Exception {
        String token = randomToken();

        RegisterVerifyError error = new RegisterVerifyError();
        error.setTokenError(true);

        when(registerService.accountVerify(userId, token)).thenReturn(Optional.of(error));

        sendRequest(get("/register/verify/{userId}/{token}", userId, token))
                .andExpect(status().isOk())
                .andExpect(request().attribute("userId", userId))
                .andExpect(view().name("forward:/helper/register/verify/resend"));

        verify(registerService, times(1)).accountVerify(userId, token);
    }

    @Test
    public void accountVerify() throws Exception {
        String token = randomToken();

        when(registerService.accountVerify(userId, token)).thenReturn(Optional.empty());

        sendRequest(get("/register/verify/{userId}/{token}", userId, token))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));

        verify(registerService, times(1)).accountVerify(userId, token);
        verify(registerService, times(1)).updateAccountToVerified(userId);
    }

    @Test
    @Ignore
    public void accountVerifyFailure() throws Exception {
        // TODO 流程要改，userService.updateEmailVerifyStatus() 回傳 false 的時候轉跳到提示頁
    }

    @Test
    public void resendVerifyEmailButNoGiveUserId() throws Exception {
        sendPostRequest("/register/verify/resend", params).andExpect(status().is4xxClientError());
    }

    @Test
    public void resendVerifyEmailSendErrorUserId() throws Exception {
        params = new HashMap<>();
        params.put("userId", new String[]{userId});

        when(registerService.resendVerifyEmail(userId)).thenReturn(false);

        sendPostRequest("/register/verify/resend", params)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").value(false));

        verify(registerService, times(1)).resendVerifyEmail(userId);
    }

    @Test
    public void resendVerifyEmail() throws Exception {
        params = new HashMap<>();
        params.put("userId", new String[]{userId});

        when(registerService.resendVerifyEmail(userId)).thenReturn(true);

        sendPostRequest("/register/verify/resend", params)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        verify(registerService, times(1)).resendVerifyEmail(userId);
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

    private String randomToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
