package com.rex.practice.dao.mapper.primary;

import com.rex.practice.dao.mapper.base.BaseMapper;
import com.rex.practice.dao.model.primary.Item;
import com.rex.practice.model.easyui.grid.ItemGridVo;

import java.util.List;

public interface ItemMapper extends BaseMapper<Item> {

    List<ItemGridVo> selectForGrid(String userId);

}