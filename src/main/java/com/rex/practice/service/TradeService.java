package com.rex.practice.service;

import com.github.pagehelper.PageInfo;
import com.rex.practice.dao.model.Trade;
import com.rex.practice.model.easyui.grid.GridPagination;
import com.rex.practice.model.easyui.grid.TradeGridVo;

public interface TradeService {

    PageInfo<TradeGridVo> getTradeGridData(GridPagination pagination, String userId);

    <E extends Trade> String save(E entity);

}
