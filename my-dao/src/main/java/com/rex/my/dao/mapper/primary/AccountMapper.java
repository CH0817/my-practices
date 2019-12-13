package com.rex.my.dao.mapper.primary;

import com.rex.my.dao.mapper.base.BaseMapper;
import com.rex.my.model.dao.primary.Account;
import com.rex.my.model.easyui.grid.AccountGridVo;

import java.util.List;

public interface AccountMapper extends BaseMapper<Account> {

    List<AccountGridVo> selectForGrid(String userId);

}