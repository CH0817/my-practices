package com.rex.my.web.controller;

import com.rex.my.web.controller.base.BaseControllerTest;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = {AccountController.class})
public class AccountControllerTest extends BaseControllerTest {

    @Test
    public void content() throws Exception {
        mvc.perform(get("/account/content"))
                .andExpect(status().isOk())
                .andExpect(view().name("page/content/account"));
    }

}
