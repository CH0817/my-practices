package com.rex.my.web.controller;

import com.rex.my.web.controller.base.BaseControllerTest;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class AccountBookControllerTest extends BaseControllerTest {

    @Test
    public void content() throws Exception {
        sendGetRequest("/account-book/content").andExpect(view().name("page/content/account_book"));
    }

    @Test
    public void getTrades() throws Exception {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("page", "1");
        paramMap.put("rows", "30");
        sendPostJsonRequestWithUserSession("/account-book/trades", paramMap)
                .andExpect(jsonPath("$.rows.length()").value(30))
                .andExpect(jsonPath("$.total").value(66));
    }

}
