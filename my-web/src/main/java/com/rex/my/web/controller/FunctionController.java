package com.rex.my.web.controller;

import com.rex.my.business.service.MenuService;
import com.rex.my.model.easyui.FunctionMenuTreeAttribute;
import com.rex.my.model.easyui.Tree;
import com.rex.my.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/function")
public class FunctionController extends BaseController {

    private MenuService service;

    @Autowired
    public FunctionController(MenuService service) {
        this.service = service;
    }

    /**
     * 取得功能列 tree JSON
     *
     * @return function menu tree JSON
     */
    @PostMapping("/menu")
    public ResponseEntity<List<Tree<FunctionMenuTreeAttribute>>> menu() {
        return ResponseEntity.ok(service.getFunctionMenuTree());
    }

}
