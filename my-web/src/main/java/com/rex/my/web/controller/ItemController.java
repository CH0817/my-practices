package com.rex.my.web.controller;

import com.rex.my.business.service.ItemService;
import com.rex.my.model.easyui.grid.DataGrid;
import com.rex.my.model.easyui.grid.GridPagination;
import com.rex.my.model.easyui.grid.ItemGridVo;
import com.rex.my.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item")
public class ItemController extends BaseController {

    private ItemService service;

    @Autowired
    public ItemController(ItemService service) {
        this.service = service;
    }

    @RequestMapping("/content")
    public String content() {
        return "page/content/item";
    }

    @RequestMapping("/list")
    @ResponseBody
    public DataGrid<ItemGridVo> getItems(GridPagination pagination) {
        return new DataGrid<>(service.getItemsForGrid(pagination, getSecuredUser().getId()));
    }

}
