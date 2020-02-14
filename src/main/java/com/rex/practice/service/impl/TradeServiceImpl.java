package com.rex.practice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rex.practice.dao.mapper.TradeMapper;
import com.rex.practice.dao.model.Trade;
import com.rex.practice.model.easyui.grid.GridPagination;
import com.rex.practice.model.easyui.grid.TradeGridVo;
import com.rex.practice.service.TradeService;
import com.rex.practice.service.base.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TradeServiceImpl extends BaseServiceImpl implements TradeService {

    private TradeMapper mapper;

    @Autowired
    public TradeServiceImpl(TradeMapper tradeMapper) {
        this.mapper = tradeMapper;
    }

    @Override
    public PageInfo<TradeGridVo> getTradeGridData(GridPagination pagination, String userId) {
        logger.info("GridPagination: {}", pagination);
        PageHelper.startPage(pagination.getPage(), pagination.getRows());
        if (StringUtils.isNotBlank(pagination.getSort()) && StringUtils.isNotBlank(pagination.getOrder())) {
            PageHelper.orderBy(pagination.getSort() + " " + pagination.getOrder());
        }
        return new PageInfo<>(mapper.selectForGrid(userId));
    }

    @Override
    public <E extends Trade> String save(E entity) {
        entity.setCreateDate(new Date());
        return mapper.insertSelective(entity) == 1 ? entity.getId() : "";
    }

}
