package com.rex.practice.service;

import com.github.pagehelper.PageInfo;
import com.rex.practice.dao.model.primary.Account;
import com.rex.practice.model.easyui.grid.AccountGridVo;
import com.rex.practice.model.easyui.grid.GridPagination;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
public interface AccountService {

    PageInfo<AccountGridVo> getAccountsForGrid(GridPagination pagination, String userId);

    <E extends Account> String save(@NotNull E input);

    boolean updateToDeleteByIds(@NotNull String[] ids, @NotBlank String userId);

}
