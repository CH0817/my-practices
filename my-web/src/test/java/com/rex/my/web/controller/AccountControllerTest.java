package com.rex.my.web.controller;

import com.github.pagehelper.PageInfo;
import com.rex.my.business.service.AccountService;
import com.rex.my.model.easyui.grid.AccountGridVo;
import com.rex.my.model.easyui.grid.GridPagination;
import com.rex.my.web.controller.base.BaseControllerTest;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {AccountController.class})
public class AccountControllerTest extends BaseControllerTest {

    @MockBean
    private AccountService service;

    @Test
    public void content() throws Exception {
        mvc.perform(get("/account/content"))
                .andExpect(status().isOk())
                .andExpect(view().name("page/content/account"));
    }

    @Test
    public void list() throws Exception {
        PageInfo<AccountGridVo> pageInfo = new PageInfo<>();
        pageInfo.setTotal(1);

        AccountGridVo vo = new AccountGridVo();
        vo.setId("a");
        vo.setAccount_type_id("a");
        vo.setMoney(new BigDecimal(500));
        vo.setName("測試現金");

        pageInfo.setList(Collections.singletonList(vo));

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("page", "1");
        paramMap.put("rows", "30");

        when(service.getAccountsForGrid(any(GridPagination.class), anyString())).thenReturn(pageInfo);

        sendPostJsonRequestWithUserSession("/account/list", paramMap)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.rows[0].id").value(vo.getId()))
                .andExpect(jsonPath("$.rows[0].name").value(vo.getName()))
                .andExpect(jsonPath("$.rows[0].money").value(vo.getMoney()))
                .andExpect(jsonPath("$.rows[0].account_type_id").value(vo.getAccount_type_id()));
    }

}
