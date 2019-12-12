package com.rex.my.web.controller;

import com.rex.my.web.controller.base.BaseControllerTest;
import org.junit.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FunctionControllerTest extends BaseControllerTest {

    @Test
    public void menu() throws Exception {
        sendPostRequest("/function/menu")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));
    }

}
