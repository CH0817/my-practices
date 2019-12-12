package com.rex.my.web.controller;

import com.rex.my.web.controller.base.BaseControllerTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class AccountBookControllerTest extends BaseControllerTest {

    @Test
    public void content() throws Exception {
        mvc.perform(get("/account-book/content"))
                .andDo(print())
                .andExpect(view().name("page/content/account_book"));
    }

    @Test
    public void getTrades() throws Exception {
        mvc.perform(post("/account-book/trades")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("rows", "30"))
                .andDo(print())
                .andExpect(jsonPath("$.rows.length()").value(30))
                .andExpect(jsonPath("$.total").value(66));
    }

}
