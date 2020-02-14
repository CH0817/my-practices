package com.rex.practice.dao.mapper;

import com.rex.practice.dao.mapper.base.BaseMapper;
import com.rex.practice.dao.model.Trade;
import com.rex.practice.model.easyui.grid.TradeGridVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TradeMapper extends BaseMapper<Trade> {

    List<TradeGridVo> selectForGrid(String userId);

}