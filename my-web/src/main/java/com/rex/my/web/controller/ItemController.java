package com.rex.my.web.controller;

import com.github.pagehelper.PageInfo;
import com.rex.my.business.service.ItemService;
import com.rex.my.model.dao.primary.User;
import com.rex.my.model.easyui.grid.DataGrid;
import com.rex.my.model.easyui.grid.GridPagination;
import com.rex.my.model.easyui.grid.ItemGridVo;
import com.rex.my.web.constant.SessionAttribute;
import com.rex.my.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

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
    public DataGrid<ItemGridVo> getItems(HttpSession session, GridPagination pagination) {
        User user = (User) session.getAttribute(SessionAttribute.USER);
        PageInfo<ItemGridVo> pageInfo = service.getItemsForGrid(pagination, user.getId());
        return new DataGrid<>(pageInfo);
    }

}
