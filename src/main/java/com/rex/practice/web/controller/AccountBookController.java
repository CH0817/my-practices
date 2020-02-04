package com.rex.practice.web.controller;

import com.rex.practice.model.easyui.grid.DataGrid;
import com.rex.practice.model.easyui.grid.GridPagination;
import com.rex.practice.model.easyui.grid.TradeGridVo;
import com.rex.practice.model.input.SaveTrade;
import com.rex.practice.service.TradeService;
import com.rex.practice.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/account-book")
public class AccountBookController extends BaseController {

    private TradeService service;

    @Autowired
    public AccountBookController(TradeService service) {
        this.service = service;
    }

    @RequestMapping("/content")
    public String content() {
        return "page/content/account_book";
    }

    @PostMapping(value = "/trades", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public DataGrid<TradeGridVo> getTrades(GridPagination pagination) {
        return new DataGrid<>(service.getTradeGridData(pagination, getSecuredUser().getId()));
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(SaveTrade input) {
        input.setUserId(getUserId());
        return ResponseEntity.ok(service.save(input));
    }

}
