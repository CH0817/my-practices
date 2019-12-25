package com.rex.my.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rex.my.business.service.AccountService;
import com.rex.my.dao.mapper.primary.AccountMapper;
import com.rex.my.model.dao.primary.Account;
import com.rex.my.model.easyui.grid.AccountGridVo;
import com.rex.my.model.easyui.grid.GridPagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountMapper mapper;

    @Autowired
    public AccountServiceImpl(AccountMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public PageInfo<AccountGridVo> getAccountsForGrid(GridPagination pagination, String userId) {
        PageHelper.startPage(pagination.getPage(), pagination.getRows());
        if (StringUtils.isNotBlank(pagination.getSort()) && StringUtils.isNotBlank(pagination.getOrder())) {
            PageHelper.orderBy(pagination.getSort() + " " + pagination.getOrder());
        }
        return new PageInfo<>(mapper.selectForGrid(userId));
    }

    @Override
    public <E extends Account> String save(E entity) {
        entity.setCreateDate(new Date());
        return 1 == mapper.insertSelective(entity) ? entity.getId() : "";
    }

    @Override
    public boolean updateToDeleteByIds(String[] ids, String userId) {
        return ids.length == mapper.updateToDeleteByIds(ids, userId);
    }

}
