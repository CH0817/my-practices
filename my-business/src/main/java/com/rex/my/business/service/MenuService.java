package com.rex.my.business.service;

import com.rex.my.model.easyui.FunctionMenuTree;
import com.rex.my.model.easyui.Tree;

import java.util.List;

public interface MenuService {

    List<Tree<FunctionMenuTree>> getFunctionMenuTree();

}
