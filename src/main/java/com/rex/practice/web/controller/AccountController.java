package com.rex.practice.web.controller;

import com.rex.practice.model.easyui.grid.AccountGridVo;
import com.rex.practice.model.easyui.grid.DataGrid;
import com.rex.practice.model.easyui.grid.GridPagination;
import com.rex.practice.model.input.SaveAccount;
import com.rex.practice.service.AccountService;
import com.rex.practice.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotEmpty;

@Controller
@RequestMapping("/account")
@Validated
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

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<String> save(@Validated SaveAccount input) {
        input.setUserId(getUserId());
        return ResponseEntity.ok().body(service.save(input));
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<Boolean> delete(@NotEmpty(message = "未選擇刪除項目") String[] ids) {
        return ResponseEntity.ok().body(service.updateToDeleteByIds(ids, getUserId()));
    }

}
