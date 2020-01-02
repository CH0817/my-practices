package com.rex.my.web.controller;

import com.github.pagehelper.PageInfo;
import com.rex.my.model.dao.primary.Account;
import com.rex.my.model.easyui.grid.GridPagination;
import com.rex.my.web.controller.base.BaseControllerTest;
import com.rex.my.web.controller.security.MockSecuredUser;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockSecuredUser
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

    @Test
    public void saveWithNoData() throws Exception {
        sendPostRequest("/account/save")
                .andExpect(jsonPath("$").value("名稱不能為空、帳戶類型不能為空"));
    }

    @Test
    public void save() throws Exception {
        String id = UUID.randomUUID().toString().replace("-", "");
        when(accountService.save(any(Account.class))).thenReturn(id);
        Map<String, String[]> params = new HashMap<>();
        params.put("name", new String[]{"測試"});
        params.put("account_type_id", new String[]{"a"});
        sendPostRequest("/account/save", params)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(id));
        verify(accountService, times(1)).save(any(Account.class));
    }

    @Test
    public void deleteByIds() throws Exception {
        when(accountService.updateToDeleteByIds(any(String[].class), eq(userId))).thenReturn(Boolean.TRUE);
        Map<String, String[]> params = new HashMap<>();
        params.put("ids", new String[]{"a", "b"});
        sendDeleteRequest("/account/delete", params)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(Boolean.TRUE));
        verify(accountService, times(1)).updateToDeleteByIds(any(String[].class), eq(userId));
    }

    @Test
    public void deleteByIdsWithNoData() throws Exception {
        sendDeleteRequest("/account/delete")
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").value("未選擇刪除項目"));
    }

}
