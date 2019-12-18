package com.rex.my.web.controller;

import com.rex.MyWebApplication;
import com.rex.my.dao.mapper.primary.UserMapper;
import com.rex.my.model.dao.primary.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MyWebApplication.class})
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    protected MockMvc mvc;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UserMapper userMapper;
    private final String TEST_EMAIL = "test@email.com";

    @Before
    public void setUser() {
        User user = new User();
        user.setId("a");
        user.setEmail(TEST_EMAIL);
        user.setPassword(passwordEncoder.encode("11111111"));
        when(userMapper.findByEmail("test@email.com")).thenReturn(user);
    }

    @Test
    public void login() throws Exception {
        mvc.perform(getFormLogin("11111111")).andDo(print()).andExpect(authenticated());
    }

    @Test
    public void invalidLogin() throws Exception {
        mvc.perform(getFormLogin("1")).andDo(print()).andExpect(unauthenticated());
    }

    private RequestBuilder getFormLogin(String password) {
        return formLogin().userParameter("email").user(TEST_EMAIL).password(password);
    }

}