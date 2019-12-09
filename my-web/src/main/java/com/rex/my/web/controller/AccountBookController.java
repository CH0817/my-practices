package com.rex.my.web.controller;

import com.rex.my.business.service.TradeService;
import com.rex.my.model.easyui.grid.DataGrid;
import com.rex.my.model.easyui.grid.GridPagination;
import com.rex.my.model.easyui.grid.TradeGridVo;
import com.rex.my.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/account-book")
public class AccountBookController extends BaseController {

    private TradeService service;

    @Autowired
    public AccountBookController(TradeService service) {
        this.service = service;
    }

    @RequestMapping("/content")
    public String content(Model model) {
        // FIXME 未完成
        return "page/content/account_book";
    }

    @PostMapping("/trades")
    @ResponseBody
    public DataGrid<TradeGridVo> trade(GridPagination pagination) {
        logger.info("pagination: {}", pagination);
        List<TradeGridVo> tradeGridData = service.getTradeGridData(pagination);
        return new DataGrid<>(66, tradeGridData);
    }

}
