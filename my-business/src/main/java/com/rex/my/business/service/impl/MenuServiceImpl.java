package com.rex.my.business.service.impl;

import com.rex.my.business.service.MenuService;
import com.rex.my.model.easyui.FunctionMenuTreeAttribute;
import com.rex.my.model.easyui.Tree;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Override
    public List<Tree<FunctionMenuTreeAttribute>> getFunctionMenuTree() {
        // FIXME 進資料庫？增加權限設定？
        // FIXME 尚未增加 attributes
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
