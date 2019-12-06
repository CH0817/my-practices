package com.rex.my.web.controller;

import com.rex.my.web.controller.base.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/function")
public abstract class FunctionController extends BaseController {

    /**
     * 取得功能列 tree JSON
     *
     * @return tree JSON
     */
    @GetMapping("/menu")
    public Object menu() {
        // FIXME 取得功能列 tree JSON
        Map<String, String> map = new HashMap<>();
        map.put("a", "a");
        map.put("b", "b");
        return map;
    }

}
