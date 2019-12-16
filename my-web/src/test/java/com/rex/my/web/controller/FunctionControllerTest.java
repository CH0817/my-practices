package com.rex.my.web.controller;

import com.rex.my.business.service.impl.MenuServiceImpl;
import com.rex.my.dao.mapper.primary.FunctionMapper;
import com.rex.my.web.controller.base.BaseControllerTest;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {FunctionController.class})
public class FunctionControllerTest extends BaseControllerTest {

    @MockBean
    private FunctionMapper mapper;
    @SpyBean
    private MenuServiceImpl service;

    @Test
    public void menu() throws Exception {
        sendPostJsonRequestWithUserSession("/function/menu").andExpect(status().isOk());
        verify(mapper, times(1)).findFunctions("a");
        verify(service, times(1)).getFunctionMenuTree("a");
    }

}
