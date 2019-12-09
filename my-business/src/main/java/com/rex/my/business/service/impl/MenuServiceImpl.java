package com.rex.my.business.service.impl;

import com.rex.my.business.service.MenuService;
import com.rex.my.model.easyui.FunctionMenuTree;
import com.rex.my.model.easyui.Tree;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Override
    public List<Tree<FunctionMenuTree>> getFunctionMenuTree() {
        // FIXME 進資料庫？增加權限設定？
        // FIXME 尚未增加 attributes
        List<Tree<FunctionMenuTree>> result = new ArrayList<>();
        List<Tree<FunctionMenuTree>> children = new ArrayList<>();

        Tree<FunctionMenuTree> tree = new Tree<>();
        tree.setId("tradeBook");
        tree.setText("收支表");
        tree.setIconCls("icon-edit");
        result.add(tree);

        tree = new Tree<>();
        tree.setId("chart");
        tree.setText("圖表");

        Tree<FunctionMenuTree> child = new Tree<>();
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
        result.add(tree);

        return result;
    }

}
