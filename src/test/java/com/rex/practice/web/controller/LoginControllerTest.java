package com.rex.practice.web.controller;

import com.rex.practice.dao.model.User;
import com.rex.practice.model.message.ErrorMessage;
import com.rex.practice.model.message.InfoMessage;
import com.rex.practice.model.message.base.Message;
import com.rex.practice.web.controller.base.BaseControllerTest;
import com.rex.practice.web.controller.security.config.MockCustomAuthenticationProvider;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({MockCustomAuthenticationProvider.class})
public class LoginControllerTest extends BaseControllerTest {

    private User user;

    @Before
    public void setUser() {
        user = new User();
        user.setId("a");
        user.setEmail("test@email.com");
        user.setPassword("{bcrypt}$2a$10$wq1FE6qSyJGi/vhQ4/b82uU.6n.g6b6mhLChG9Xb8K5rcKgLyeZcq");
    }

    @Test
    public void login() throws Exception {
        when(userService.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userService.isEmailVerified(anyString())).thenReturn(true);

        mvc.perform(getLoginForm(user.getEmail()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/main"))
                .andExpect(authenticated());
    }

    @Test
    public void invalidLogin() throws Exception {
        user.setPassword("{bcrypt}111111111");

        when(userService.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userService.isEmailVerified(anyString())).thenReturn(true);

        ErrorMessage expectMessage = new ErrorMessage("錯誤的Email或密碼", "/login");

        verifyLoginHasProblem(expectMessage, user.getEmail()).andExpect(unauthenticated());
    }

    @Test
    public void loginButNotRegister() throws Exception {
        when(userService.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        ErrorMessage expectMessage = new ErrorMessage("此Email尚未註冊", "/login");

        verifyLoginHasProblem(expectMessage, "test_02@email.com").andExpect(unauthenticated());
    }

    @Test
    public void loginButNotVerifyEmail() throws Exception {
        when(userService.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userService.isEmailVerified(anyString())).thenReturn(false);

        InfoMessage expectMessage = new InfoMessage("Email尚未驗證", "/main");

        verifyLoginHasProblem(expectMessage, user.getEmail()).andExpect(authenticated());
    }

    private ResultActions verifyLoginHasProblem(Message expectMessage, String email) throws Exception {
        return mvc.perform(getLoginForm(email))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("helper/show/info"))
                .andExpect(request().attribute("message", allOf(
                        hasProperty("message", is(expectMessage.getMessage())),
                        hasProperty("redirectUrl", is(expectMessage.getRedirectUrl())),
                        hasProperty("icon", is(expectMessage.getIcon()))
                )));
    }

    private RequestBuilder getLoginForm(String email) {
        return formLogin().userParameter("email").user(email).password("11111111");
    }

}