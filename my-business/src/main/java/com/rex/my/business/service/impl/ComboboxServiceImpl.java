package com.rex.my.business.service.impl;

import com.rex.my.business.service.ComboboxService;
import com.rex.my.dao.mapper.primary.AccountMapper;
import com.rex.my.dao.mapper.primary.ItemMapper;
import com.rex.my.model.constant.TradeTypeEnum;
import com.rex.my.model.easyui.combobox.ComboboxData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ComboboxServiceImpl implements ComboboxService {

    private AccountMapper accountMapper;
    private ItemMapper itemMapper;

    @Autowired
    public ComboboxServiceImpl(AccountMapper accountMapper, ItemMapper itemMapper) {
        this.accountMapper = accountMapper;
        this.itemMapper = itemMapper;
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

}
