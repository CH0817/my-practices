package com.rex.my.web.controller;

import com.rex.my.business.service.AccountService;
import com.rex.my.model.easyui.grid.AccountGridVo;
import com.rex.my.model.easyui.grid.DataGrid;
import com.rex.my.model.easyui.grid.GridPagination;
import com.rex.my.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/account")
public class AccountController extends BaseController {

    private AccountService service;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    @RequestMapping("/content")
    public String content() {
        return "page/content/account";
    }

    @RequestMapping("/list")
    @ResponseBody
    public DataGrid<AccountGridVo> list(GridPagination pagination) {
        return new DataGrid<>(service.getAccountsForGrid(pagination, getSecuredUser().getId()));
    }

}
