package com.rex.practice.dao.mapper.primary;

import com.rex.practice.dao.mapper.base.BaseMapper;
import com.rex.practice.dao.model.primary.Trade;
import com.rex.practice.model.easyui.grid.TradeGridVo;

import java.util.List;

public interface TradeMapper extends BaseMapper<Trade> {

    List<TradeGridVo> selectForGrid(String userId);

}