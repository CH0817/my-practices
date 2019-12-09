package com.rex.my.business.service;

import com.github.pagehelper.PageInfo;
import com.rex.my.model.easyui.grid.GridPagination;
import com.rex.my.model.easyui.grid.TradeGridVo;

public interface TradeService {

    PageInfo<TradeGridVo> getTradeGridData(GridPagination pagination);

}
