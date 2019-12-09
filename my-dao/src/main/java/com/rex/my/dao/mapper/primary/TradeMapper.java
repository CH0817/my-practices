package com.rex.my.dao.mapper.primary;

import com.rex.my.dao.mapper.base.BaseMapper;
import com.rex.my.model.dao.primary.Trade;
import com.rex.my.model.easyui.grid.GridPagination;

import java.util.List;

public interface TradeMapper extends BaseMapper<Trade> {

    List<Trade> selectForGrid(GridPagination pagination);

}