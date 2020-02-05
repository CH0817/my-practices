package com.rex.practice.dao.mapper;

import com.rex.practice.dao.mapper.base.BaseMapper;
import com.rex.practice.dao.model.primary.Account;
import com.rex.practice.model.easyui.grid.AccountGridVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    List<AccountGridVo> selectForGrid(String userId);

}