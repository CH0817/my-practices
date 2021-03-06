package com.rex.practice.web.controller;

import com.rex.practice.model.easyui.grid.DataGrid;
import com.rex.practice.model.easyui.grid.GridPagination;
import com.rex.practice.model.easyui.grid.ItemGridVo;
import com.rex.practice.model.input.SaveItem;
import com.rex.practice.model.input.UpdateItem;
import com.rex.practice.service.ItemService;
import com.rex.practice.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

@Controller
@RequestMapping("/item")
@Validated
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

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<String> save(@Validated SaveItem item) {
        item.setUserId(getUserId());
        return ResponseEntity.ok().body(service.save(item));
    }

    @DeleteMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<Boolean> delete(@NotEmpty(message = "未選擇刪除項目") String[] ids) {
        return ResponseEntity.ok().body(service.updateToDeleteByIds(ids, getUserId()));
    }

    @PutMapping(value = "/update", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<Boolean> update(@Validated UpdateItem item) {
        item.setUserId(getUserId());
        return ResponseEntity.ok().body(service.updateById(item));
    }

}
