package com.rex.my.web.controller;

import com.github.pagehelper.PageInfo;
import com.rex.my.business.service.TradeService;
import com.rex.my.model.easyui.grid.GridPagination;
import com.rex.my.model.easyui.grid.TradeGridVo;
import com.rex.my.web.controller.base.BaseControllerTest;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = {AccountBookController.class})
public class AccountBookControllerTest extends BaseControllerTest {

    @MockBean
    private TradeService service;

    @Test
    public void content() throws Exception {
        sendGetRequest("/account-book/content").andExpect(view().name("page/content/account_book"));
    }

    @Test
    public void getTrades() throws Exception {
        PageInfo<TradeGridVo> pageInfo = new PageInfo<>();
        pageInfo.setTotal(66);
        pageInfo.setList(IntStream.range(0, 30).mapToObj(i -> new TradeGridVo()).collect(Collectors.toList()));
        when(service.getTradeGridData(any(GridPagination.class), anyString())).thenReturn(pageInfo);
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("page", "1");
        paramMap.put("rows", "30");
        sendPostJsonRequestWithUserSession("/account-book/trades", paramMap)
                .andExpect(jsonPath("$.rows.length()").value(30))
                .andExpect(jsonPath("$.total").value(66));
    }

}
