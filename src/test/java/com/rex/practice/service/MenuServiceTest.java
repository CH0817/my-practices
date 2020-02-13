package com.rex.practice.service;

import com.rex.practice.dao.model.Functions;
import com.rex.practice.model.easyui.FunctionMenuTreeAttribute;
import com.rex.practice.model.easyui.Tree;
import com.rex.practice.service.base.BaseServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MenuServiceTest extends BaseServiceTest {

    @Autowired
    private MenuService service;

    @Test
    public void getFunctionMenuTree() {
        when(functionMapper.findFunctions("a")).thenReturn(fundFunctionsResult());

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

        verify(functionMapper, times(1)).findFunctions("a");
    }

    private List<Functions> fundFunctionsResult() {
        List<Functions> result = new ArrayList<>();

        Functions function = new Functions();
        function.setId("a");
        function.setName("收支表");
        function.setUrl("account-book/content");
        function.setIcon("icon-edit");
        function.setSorted((short) 1);
        result.add(function);

        function = new Functions();
        function.setId("b");
        function.setName("圖表");
        function.setSorted((short) 2);
        result.add(function);

        function = new Functions();
        function.setId("c");
        function.setName("長條圖");
        function.setUrl("chart/bar");
        function.setSorted((short) 1);
        function.setParentId("b");
        result.add(function);

        function = new Functions();
        function.setId("d");
        function.setName("圓餅圖");
        function.setUrl("chart/pie");
        function.setSorted((short) 1);
        function.setParentId("b");
        result.add(function);

        function = new Functions();
        function.setId("e");
        function.setName("設定");
        function.setSorted((short) 3);
        result.add(function);

        function = new Functions();
        function.setId("f");
        function.setName("帳戶");
        function.setUrl("account/content");
        function.setSorted((short) 1);
        function.setParentId("e");
        result.add(function);

        function = new Functions();
        function.setId("g");
        function.setName("項目");
        function.setUrl("item/content");
        function.setSorted((short) 2);
        function.setParentId("e");
        result.add(function);

        function = new Functions();
        function.setId("h");
        function.setName("登出");
        function.setUrl("logout");
        function.setSorted((short) 4);
        result.add(function);

        return result;
    }

}
