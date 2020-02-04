package com.rex.practice.dao.mapper.primary;

import com.rex.practice.dao.mapper.base.BaseMapper;
import com.rex.practice.dao.model.primary.Account;
import com.rex.practice.model.easyui.grid.AccountGridVo;

import java.util.List;

public interface AccountMapper extends BaseMapper<Account> {

    List<AccountGridVo> selectForGrid(String userId);

}