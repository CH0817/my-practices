package com.rex.my.business.service;

import com.github.pagehelper.PageInfo;
import com.rex.my.model.easyui.grid.GridPagination;
import com.rex.my.model.easyui.grid.ItemGridVo;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
public interface ItemService {

    PageInfo<ItemGridVo> getItemsForGrid(GridPagination pagination, String userId);

    String save(@NotBlank String name, @NotBlank String userId);

}
