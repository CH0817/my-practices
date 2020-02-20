package com.rex.practice.web.controller;

import com.rex.practice.model.input.Register;
import com.rex.practice.model.message.ErrorMessage;
import com.rex.practice.model.message.InfoMessage;
import com.rex.practice.model.message.base.Message;
import com.rex.practice.model.verify.RegisterVerifyError;
import com.rex.practice.web.controller.base.BaseControllerTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
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
        when(registerService.verify(any(Register.class), any(BindingResult.class))).thenReturn(Optional.empty());
        when(registerService.register(any(Register.class))).thenReturn(true);

        sendRegisterRequest(new InfoMessage("註冊成功，請至信箱收取驗證信", "/login"));

        verify(registerService, times(1)).verify(any(Register.class), any(BindingResult.class));
        verify(registerService, times(1)).register(any(Register.class));
    }

    @Test
    public void registerButEmailIsEmpty() throws Exception {
        registerVerifyError(new ErrorMessage("Email不能為空", "/register"));
    }

    @Test
    public void registerButEmailFormatError() throws Exception {
        registerVerifyError(new ErrorMessage("錯誤的Email格式", "/register"));
    }

    @Test
    public void registerButPasswordIsEmpty() throws Exception {
        registerVerifyError(new ErrorMessage("密碼不能為空，密碼必須是8~12碼", "/register"));
    }

    @Test
    public void registerButPasswordFormatError() throws Exception {
        registerVerifyError(new ErrorMessage("密碼必須是8~12碼", "/register"));
    }

    @Test
    public void registerButConfirmPasswordIsEmpty() throws Exception {
        registerVerifyError(new ErrorMessage("確認密碼不能為空", "/register"));
    }

    @Test
    public void registerButPasswordDifferent() throws Exception {
        registerVerifyError(new ErrorMessage("兩次密碼不相同", "/register"));
    }

    @Test
    public void registerButEmailRegistered() throws Exception {
        registerVerifyError(new ErrorMessage("Email已被註冊", "/register"));
    }

    @Test
    public void registerButEmailVerifying() throws Exception {
        registerVerifyError(new ErrorMessage("Email驗證中", "/login"));
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

    private void sendRegisterRequest(Message expectMessage) throws Exception {
        ResultActions resultActions = sendPostRequest("/register/create", params)
                .andExpect(status().isOk())
                .andExpect(view().name("forward:/helper/show/info"));
        verifyForwardMessage(resultActions, expectMessage);
    }

    private void registerVerifyError(Message expectMessage) throws Exception {
        Optional<Message> optional = Optional.of(expectMessage);

        when(registerService.verify(any(Register.class), any(BindingResult.class))).thenReturn(optional);

        sendRegisterRequest(expectMessage);

        verify(registerService, times(1)).verify(any(Register.class), any(BindingResult.class));
        verify(registerService, never()).register(any(Register.class));
    }

    private String randomToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
