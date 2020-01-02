package com.rex.my.web.controller;

import com.rex.my.web.controller.base.BaseControllerTest;
import com.rex.my.web.controller.security.MockSecuredUser;
import org.junit.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockSecuredUser
public class FunctionControllerTest extends BaseControllerTest {

    @Test
    public void menu() throws Exception {
        sendPostJsonRequest("/function/menu").andExpect(status().isOk());
        verify(menuService, times(1)).getFunctionMenuTree("a");
    }

}
