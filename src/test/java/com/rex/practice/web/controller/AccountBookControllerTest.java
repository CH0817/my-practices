package com.rex.practice.web.controller;

import com.github.pagehelper.PageInfo;
import com.rex.practice.model.easyui.grid.GridPagination;
import com.rex.practice.model.input.SaveTrade;
import com.rex.practice.web.controller.base.BaseControllerTest;
import com.rex.practice.web.controller.security.MockSecuredUser;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockSecuredUser
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

    @Test
    public void save() throws Exception {
        String id = UUID.randomUUID().toString();
        when(tradeService.save(any(SaveTrade.class))).thenReturn(id);

        Map<String, String[]> params = new HashMap<>();
        params.put("money", new String[]{"1000.00"});
        params.put("trade_type", new String[]{"1"});
        params.put("trade_date", new String[]{"2020/1/7"});
        params.put("account_id", new String[]{"b"});
        params.put("item_id", new String[]{"b"});

        sendPostRequest("/account-book/save", params).andExpect(jsonPath("$").value(id));
    }

}
