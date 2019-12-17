package com.rex.my.business.service;

import com.rex.my.business.service.base.BaseServiceTest;
import com.rex.my.model.dao.primary.Function;
import com.rex.my.model.easyui.FunctionMenuTreeAttribute;
import com.rex.my.model.easyui.Tree;
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

    private List<Function> fundFunctionsResult() {
        List<Function> result = new ArrayList<>();

        Function function = new Function();
        function.setId("a");
        function.setName("收支表");
        function.setUrl("account-book/content");
        function.setIcon("icon-edit");
        function.setSorted((short) 1);
        result.add(function);

        function = new Function();
        function.setId("b");
        function.setName("圖表");
        function.setSorted((short) 2);
        result.add(function);

        function = new Function();
        function.setId("c");
        function.setName("長條圖");
        function.setUrl("chart/bar");
        function.setSorted((short) 1);
        function.setParentId("b");
        result.add(function);

        function = new Function();
        function.setId("d");
        function.setName("圓餅圖");
        function.setUrl("chart/pie");
        function.setSorted((short) 1);
        function.setParentId("b");
        result.add(function);

        function = new Function();
        function.setId("e");
        function.setName("設定");
        function.setSorted((short) 3);
        result.add(function);

        function = new Function();
        function.setId("f");
        function.setName("帳戶");
        function.setUrl("account/content");
        function.setSorted((short) 1);
        function.setParentId("e");
        result.add(function);

        function = new Function();
        function.setId("g");
        function.setName("項目");
        function.setUrl("item/content");
        function.setSorted((short) 2);
        function.setParentId("e");
        result.add(function);

        function = new Function();
        function.setId("h");
        function.setName("登出");
        function.setUrl("logout");
        function.setSorted((short) 4);
        result.add(function);

        return result;
    }

}
