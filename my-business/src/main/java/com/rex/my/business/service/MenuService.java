package com.rex.my.business.service;

import com.rex.my.model.easyui.FunctionMenuTreeAttribute;
import com.rex.my.model.easyui.Tree;

import java.util.List;

public interface MenuService {

    List<Tree<FunctionMenuTreeAttribute>> getFunctionMenuTree(String userId);

}
