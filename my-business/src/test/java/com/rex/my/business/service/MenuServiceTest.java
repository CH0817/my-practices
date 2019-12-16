package com.rex.my.business.service;

import com.rex.my.business.service.base.BaseServiceTest;
import com.rex.my.model.easyui.FunctionMenuTreeAttribute;
import com.rex.my.model.easyui.Tree;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MenuServiceTest extends BaseServiceTest {

    @Autowired
    private MenuService service;

    @Test
    public void getFunctionMenuTree() {
        List<Tree<FunctionMenuTreeAttribute>> result = service.getFunctionMenuTree("a");
        assertEquals(4, result.size());
        assertEquals(2, result.get(1).getChildren().size());
        assertEquals(2, result.get(2).getChildren().size());
        assertEquals("收支表", result.get(0).getText());
        assertEquals("account-book/content", result.get(0).getAttributes().getUrl());
        assertEquals("圖表", result.get(1).getText());
        assertEquals("長條圖", result.get(1).getChildren().get(0).getText());
        assertEquals("chart/bar", result.get(1).getChildren().get(0).getAttributes().getUrl());
        assertEquals("圓餅圖", result.get(1).getChildren().get(1).getText());
        assertEquals("chart/pie", result.get(1).getChildren().get(1).getAttributes().getUrl());
        assertEquals("設定", result.get(2).getText());
        assertEquals("帳戶", result.get(2).getChildren().get(0).getText());
        assertEquals("account/content", result.get(2).getChildren().get(0).getAttributes().getUrl());
        assertEquals("項目", result.get(2).getChildren().get(1).getText());
        assertEquals("item/content", result.get(2).getChildren().get(1).getAttributes().getUrl());
        assertEquals("登出", result.get(3).getText());
    }

}
