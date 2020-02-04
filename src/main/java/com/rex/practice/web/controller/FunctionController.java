package com.rex.practice.web.controller;

import com.rex.practice.model.easyui.FunctionMenuTreeAttribute;
import com.rex.practice.model.easyui.Tree;
import com.rex.practice.service.MenuService;
import com.rex.practice.web.controller.base.BaseController;
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
        return ResponseEntity.ok(service.getFunctionMenuTree(getSecuredUser().getId()));
    }

}
