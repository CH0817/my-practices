package com.rex.my.business.service;

import com.github.pagehelper.PageInfo;
import com.rex.my.business.service.base.BaseServiceTest;
import com.rex.my.model.easyui.grid.AccountGridVo;
import com.rex.my.model.easyui.grid.GridPagination;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AccountServiceTest extends BaseServiceTest {

    @Autowired
    private AccountService service;

    @Test
    public void selectForGrid() {
        String userId = "a";

        List<AccountGridVo> returnAccounts = new ArrayList<>();

        AccountGridVo account = new AccountGridVo();
        account.setId("d");
        account.setMoney(new BigDecimal(1000));
        account.setAccount_type_id("a");
        account.setName("現金");

        returnAccounts.add(account);

        when(accountMapper.selectForGrid(userId)).thenReturn(returnAccounts);

        GridPagination pagination = new GridPagination();
        pagination.setPage(1);
        pagination.setRows(30);
        pagination.setSort("money");
        pagination.setOrder("desc");

        PageInfo<AccountGridVo> result = service.getAccountsForGrid(pagination, "a");

        verify(accountMapper, atLeast(1)).selectForGrid(anyString());

        assertEquals(1, result.getTotal());
        assertEquals("id 不相同", account.getId(), result.getList().get(0).getId());
        assertEquals("name 不相同", account.getName(), result.getList().get(0).getName());
        assertEquals("account type id 不相同", account.getAccount_type_id(), result.getList().get(0).getAccount_type_id());
        assertEquals("money 不相同", 0, account.getMoney().compareTo(result.getList().get(0).getMoney()));
    }

}