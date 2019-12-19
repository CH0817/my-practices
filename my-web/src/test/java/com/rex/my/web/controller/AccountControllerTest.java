package com.rex.my.web.controller;

import com.github.pagehelper.PageInfo;
import com.rex.my.model.easyui.grid.GridPagination;
import com.rex.my.web.controller.base.BaseControllerTest;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class AccountControllerTest extends BaseControllerTest {

    @Test
    public void content() throws Exception {
        sendGetRequest("/account/content").andExpect(view().name("page/content/account"));
    }

    @Test
    public void list() throws Exception {
        when(accountService.getAccountsForGrid(any(GridPagination.class), eq("a"))).thenReturn(new PageInfo<>());
        sendPaginationPostJsonRequest("/account/list").andExpect(status().isOk());
        verify(accountService, times(1)).getAccountsForGrid(any(GridPagination.class), eq("a"));
    }

}
