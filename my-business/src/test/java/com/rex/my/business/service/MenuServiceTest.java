package com.rex.my.business.service;

import com.rex.my.business.service.base.BaseServiceTest;
import com.rex.my.model.easyui.FunctionMenuTree;
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
        List<Tree<FunctionMenuTree>> functionMenuTree = service.getFunctionMenuTree();
        assertEquals(4, functionMenuTree.size());
    }

}
