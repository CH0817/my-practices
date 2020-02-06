package com.rex.practice.service;

import com.github.pagehelper.PageInfo;
import com.rex.practice.dao.model.primary.Item;
import com.rex.practice.model.easyui.grid.GridPagination;
import com.rex.practice.model.easyui.grid.ItemGridVo;

public interface ItemService {

    PageInfo<ItemGridVo> getItemsForGrid(GridPagination pagination, String userId);

    <E extends Item> String save(E entity);

    boolean updateToDeleteByIds(String[] ids, String userId);

    <E extends Item> Boolean updateById(E entity);

}
