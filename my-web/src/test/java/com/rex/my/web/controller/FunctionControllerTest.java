package com.rex.my.web.controller;

import com.rex.my.business.service.MenuService;
import com.rex.my.model.easyui.FunctionMenuTreeAttribute;
import com.rex.my.model.easyui.Tree;
import com.rex.my.web.controller.base.BaseControllerTest;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {FunctionController.class})
public class FunctionControllerTest extends BaseControllerTest {

    @MockBean
    private MenuService service;

    @Test
    public void menu() throws Exception {
        // FIXME 增加驗證？
        when(service.getFunctionMenuTree()).thenReturn(createReturn());
        sendPostRequest("/function/menu")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));
    }

    private List<Tree<FunctionMenuTreeAttribute>> createReturn() {
        List<Tree<FunctionMenuTreeAttribute>> result = new ArrayList<>();
        List<Tree<FunctionMenuTreeAttribute>> children = new ArrayList<>();
        Tree<FunctionMenuTreeAttribute> tree = new Tree<>();
        tree.setId("tradeBook");
        tree.setText("收支表");
        tree.setIconCls("icon-edit");
        tree.setChecked(Boolean.TRUE);

        FunctionMenuTreeAttribute attribute = new FunctionMenuTreeAttribute();
        attribute.setUrl("account-book/content");

        tree.setAttributes(attribute);

        result.add(tree);

        tree = new Tree<>();
        tree.setId("chart");
        tree.setText("圖表");

        Tree<FunctionMenuTreeAttribute> child = new Tree<>();
        child.setId("barChart");
        child.setText("長條圖");
        children.add(child);

        child = new Tree<>();
        child.setId("pieChart");
        child.setText("圓餅圖");
        children.add(child);

        tree.setChildren(children);
        result.add(tree);

        children = new ArrayList<>();

        tree = new Tree<>();
        tree.setId("settings");
        tree.setText("設定");

        child = new Tree<>();
        child.setId("account");
        child.setText("帳戶");
        children.add(child);

        child = new Tree<>();
        child.setId("item");
        child.setText("項目");
        children.add(child);

        tree.setChildren(children);
        result.add(tree);

        tree = new Tree<>();
        tree.setId("logout");
        tree.setText("登出");

        attribute = new FunctionMenuTreeAttribute();
        attribute.setUrl("logout");

        tree.setAttributes(attribute);

        result.add(tree);
        return result;
    }

}
