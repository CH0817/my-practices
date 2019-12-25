package com.rex.my.dao.mapper.primary;

import com.rex.my.dao.mapper.base.BaseMapper;
import com.rex.my.model.dao.primary.Item;
import com.rex.my.model.easyui.grid.ItemGridVo;

import java.util.List;

public interface ItemMapper extends BaseMapper<Item> {

    List<ItemGridVo> selectForGrid(String userId);

}