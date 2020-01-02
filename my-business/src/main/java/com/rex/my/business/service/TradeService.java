package com.rex.my.business.service;

import com.github.pagehelper.PageInfo;
import com.rex.my.model.dao.primary.Trade;
import com.rex.my.model.easyui.grid.GridPagination;
import com.rex.my.model.easyui.grid.TradeGridVo;
import com.rex.my.model.input.SaveTrade;

public interface TradeService {

    PageInfo<TradeGridVo> getTradeGridData(GridPagination pagination, String userId);

    <E extends Trade> String save(E entity);

}
