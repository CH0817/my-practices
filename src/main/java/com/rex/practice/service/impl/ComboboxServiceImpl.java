package com.rex.practice.service.impl;

import com.rex.practice.constant.TradeTypeEnum;
import com.rex.practice.dao.mapper.AccountMapper;
import com.rex.practice.dao.mapper.AccountTypeMapper;
import com.rex.practice.dao.mapper.ItemMapper;
import com.rex.practice.model.easyui.combobox.ComboboxData;
import com.rex.practice.service.ComboboxService;
import com.rex.practice.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ComboboxServiceImpl extends BaseServiceImpl implements ComboboxService {

    private AccountMapper accountMapper;
    private ItemMapper itemMapper;
    private AccountTypeMapper accountTypeMapper;

    @Autowired
    public ComboboxServiceImpl(AccountMapper accountMapper, ItemMapper itemMapper, AccountTypeMapper accountTypeMapper) {
        this.accountMapper = accountMapper;
        this.itemMapper = itemMapper;
        this.accountTypeMapper = accountTypeMapper;
    }

    @Override
    public List<ComboboxData> getTradeTypeComboboxData() {
        return Stream.of(TradeTypeEnum.values()).map(ComboboxData::new).collect(Collectors.toList());
    }

    @Override
    public List<ComboboxData> getAccountComboboxData(String userId) {
        return accountMapper.selectAll(userId).stream().map(ComboboxData::new).collect(Collectors.toList());
    }

    @Override
    public List<ComboboxData> getItemComboboxData(String userId) {
        return itemMapper.selectAll(userId).stream().map(ComboboxData::new).collect(Collectors.toList());
    }

    @Override
    public List<ComboboxData> getAccountTypeComboboxData(String userId) {
        return accountTypeMapper.selectAll(userId).stream().map(ComboboxData::new).collect(Collectors.toList());
    }

}
