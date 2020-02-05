package com.rex.practice.dao.mapper;

import com.rex.practice.dao.mapper.base.BaseMapper;
import com.rex.practice.dao.model.primary.Item;
import com.rex.practice.model.easyui.grid.ItemGridVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper extends BaseMapper<Item> {

    List<ItemGridVo> selectForGrid(String userId);

}