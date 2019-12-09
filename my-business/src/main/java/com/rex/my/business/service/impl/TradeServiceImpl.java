package com.rex.my.business.service.impl;

import com.rex.my.business.service.TradeService;
import com.rex.my.dao.mapper.primary.AccountMapper;
import com.rex.my.dao.mapper.primary.ItemMapper;
import com.rex.my.dao.mapper.primary.TradeMapper;
import com.rex.my.model.constant.TradeTypeEnum;
import com.rex.my.model.dao.primary.Trade;
import com.rex.my.model.easyui.grid.GridPagination;
import com.rex.my.model.easyui.grid.TradeGridVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TradeServiceImpl implements TradeService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private TradeMapper tradeMapper;
    private AccountMapper accountMapper;
    private ItemMapper itemMapper;

    @Autowired
    public TradeServiceImpl(TradeMapper tradeMapper, AccountMapper accountMapper, ItemMapper itemMapper) {
        this.tradeMapper = tradeMapper;
        this.accountMapper = accountMapper;
        this.itemMapper = itemMapper;
    }

    @Override
    public List<TradeGridVo> getTradeGridData(GridPagination pagination) {
        List<Trade> result = tradeMapper.selectForGrid(pagination);
        logger.info("result size: {}", result.size());
        return result.stream().map(this::transfer2TradeGridVo).collect(Collectors.toList());
    }

    private TradeGridVo transfer2TradeGridVo(Trade trade) {
        TradeGridVo result = new TradeGridVo();
        result.setId(trade.getId());
        result.setMoney(trade.getMoney().toString());
        result.setTradeType(TradeTypeEnum.getDescriptionByCode(trade.getTradeType()));
        result.setTradeDate(date2String(trade.getTradeDate()));
        result.setAccountId(trade.getAccountId());
        result.setItemId(trade.getItemId());
        result.setAccountName(accountMapper.selectByPrimaryKey(trade.getAccountId()).getName());
        result.setItemName(itemMapper.selectByPrimaryKey(trade.getItemId()).getName());
        return result;
    }

    private String date2String(Date date) {
        assert Objects.nonNull(date);
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

}
