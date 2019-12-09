package com.rex.my.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rex.my.business.service.TradeService;
import com.rex.my.dao.mapper.primary.TradeMapper;
import com.rex.my.model.easyui.grid.GridPagination;
import com.rex.my.model.easyui.grid.TradeGridVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeServiceImpl implements TradeService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private TradeMapper mapper;

    @Autowired
    public TradeServiceImpl(TradeMapper tradeMapper) {
        this.mapper = tradeMapper;
    }

    @Override
    public PageInfo<TradeGridVo> getTradeGridData(GridPagination pagination) {
        logger.info("GridPagination: {}", pagination);
        PageHelper.startPage(pagination.getPage(), pagination.getRows());
        if (StringUtils.isNotBlank(pagination.getSort()) && StringUtils.isNotBlank(pagination.getOrder())) {
            PageHelper.orderBy(pagination.getSort() + " " + pagination.getOrder());
        }
        return new PageInfo<>(mapper.selectForGrid());
    }

}
