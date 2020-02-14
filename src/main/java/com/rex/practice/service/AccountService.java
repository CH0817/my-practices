package com.rex.practice.service;

import com.github.pagehelper.PageInfo;
import com.rex.practice.dao.model.Account;
import com.rex.practice.model.easyui.grid.AccountGridVo;
import com.rex.practice.model.easyui.grid.GridPagination;

public interface AccountService {

    PageInfo<AccountGridVo> getAccountsForGrid(GridPagination pagination, String userId);

    <E extends Account> String save(E input);

    boolean updateToDeleteByIds(String[] ids, String userId);

}
