package com.rex.practice.service;

import com.github.pagehelper.PageInfo;
import com.rex.practice.dao.model.primary.Item;
import com.rex.practice.model.easyui.grid.GridPagination;
import com.rex.practice.model.easyui.grid.ItemGridVo;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Validated
public interface ItemService {

    PageInfo<ItemGridVo> getItemsForGrid(GridPagination pagination, String userId);

    <E extends Item> String save(@NotNull E entity);

    boolean updateToDeleteByIds(@NotEmpty String[] ids, @NotBlank String userId);

    <E extends Item> Boolean updateById(E entity);

}
