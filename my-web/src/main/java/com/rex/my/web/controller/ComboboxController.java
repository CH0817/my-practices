package com.rex.my.web.controller;

import com.rex.my.business.service.ComboboxService;
import com.rex.my.model.dao.primary.User;
import com.rex.my.model.easyui.combobox.ComboboxData;
import com.rex.my.web.constant.SessionAttribute;
import com.rex.my.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/combobox", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ComboboxController extends BaseController {

    private ComboboxService service;

    @Autowired
    public ComboboxController(ComboboxService service) {
        this.service = service;
    }

    @GetMapping("/trade/types")
    @ResponseBody
    public List<ComboboxData> getTradeTypes() {
        return service.getTradeTypeComboboxData();
    }

    @GetMapping("/accounts")
    @ResponseBody
    public List<ComboboxData> getAccounts(HttpSession session) {
        return service.getAccountComboboxData(getSessionUser(session).getId());
    }

    @GetMapping("/items")
    @ResponseBody
    public List<ComboboxData> getItems(HttpSession session) {
        return service.getItemComboboxData(getSessionUser(session).getId());
    }
    @GetMapping("/account/types")
    @ResponseBody
    public List<ComboboxData> getAccountTypes(HttpSession session) {
        return service.getAccountTypeComboboxData(getSessionUser(session).getId());
    }

    private User getSessionUser(HttpSession session) {
        User user = (User) session.getAttribute(SessionAttribute.USER);
        assert Objects.nonNull(user);
        return user;
    }

}
