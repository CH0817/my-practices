package com.rex.practice.service;

import com.rex.practice.model.easyui.FunctionMenuTreeAttribute;
import com.rex.practice.model.easyui.Tree;

import java.util.List;

public interface MenuService {

    List<Tree<FunctionMenuTreeAttribute>> getFunctionMenuTree(String userId);

}
