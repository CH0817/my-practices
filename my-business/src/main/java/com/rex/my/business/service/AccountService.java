package com.rex.my.business.service;

import com.github.pagehelper.PageInfo;
import com.rex.my.model.dao.primary.Account;
import com.rex.my.model.dao.primary.AccountType;
import com.rex.my.model.easyui.grid.AccountGridVo;
import com.rex.my.model.easyui.grid.GridPagination;
import com.rex.my.model.input.SaveAccount;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface AccountService {

    PageInfo<AccountGridVo> getAccountsForGrid(GridPagination pagination, String userId);

    <E extends Account> String save(@NotNull E input);

}
