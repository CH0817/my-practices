package com.rex.my.web.controller;

import com.github.pagehelper.PageInfo;
import com.rex.my.model.easyui.grid.GridPagination;
import com.rex.my.web.controller.base.BaseControllerTest;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class AccountBookControllerTest extends BaseControllerTest {

    @Test
    public void content() throws Exception {
        sendGetRequest("/account-book/content").andExpect(view().name("page/content/account_book"));
    }

    @Test
    public void getTrades() throws Exception {
        when(tradeService.getTradeGridData(any(GridPagination.class), eq("a"))).thenReturn(new PageInfo<>());
        sendPaginationPostJsonRequest("/account-book/trades").andExpect(status().isOk());
        verify(tradeService, times(1)).getTradeGridData(any(GridPagination.class), eq("a"));
    }

}
