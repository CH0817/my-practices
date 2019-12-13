package com.rex.my.business.service;

import com.github.pagehelper.PageInfo;
import com.rex.my.model.easyui.grid.AccountGridVo;
import com.rex.my.model.easyui.grid.GridPagination;

public interface AccountService {

    PageInfo<AccountGridVo> getAccountsForGrid(GridPagination pagination, String userId);

}
