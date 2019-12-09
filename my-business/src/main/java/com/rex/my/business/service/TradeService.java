package com.rex.my.business.service;

import com.rex.my.model.easyui.grid.GridPagination;
import com.rex.my.model.easyui.grid.TradeGridVo;

import java.util.List;

public interface TradeService {

    List<TradeGridVo> getTradeGridData(GridPagination pagination);

}
