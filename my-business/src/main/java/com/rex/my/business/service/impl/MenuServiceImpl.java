package com.rex.my.business.service.impl;

import com.rex.my.business.service.MenuService;
import com.rex.my.dao.mapper.primary.FunctionMapper;
import com.rex.my.model.dao.primary.Function;
import com.rex.my.model.easyui.FunctionMenuTreeAttribute;
import com.rex.my.model.easyui.Tree;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    private FunctionMapper mapper;

    @Autowired
    public MenuServiceImpl(FunctionMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Tree<FunctionMenuTreeAttribute>> getFunctionMenuTree(String userId) {
        List<Function> functions = mapper.findFunctions(userId);
        List<Tree<FunctionMenuTreeAttribute>> result = functions.stream()
                .filter(f -> StringUtils.isBlank(f.getParentId()))
                .sorted(Comparator.comparing(Function::getSorted))
                .map(createTree(functions))
                .collect(Collectors.toList());
        return result;
    }

    private java.util.function.Function<Function, Tree<FunctionMenuTreeAttribute>> createTree(List<Function> allFunctions) {
        return function -> {
            Tree<FunctionMenuTreeAttribute> tree = new Tree<>(function);
            tree.setAttributes(createAttribute(function.getUrl()));
            tree.setChildren(createChildrenTree(tree, allFunctions));
            return tree;
        };
    }

    private FunctionMenuTreeAttribute createAttribute(String url) {
        FunctionMenuTreeAttribute result = null;
        if (StringUtils.isNotBlank(url)) {
            result = new FunctionMenuTreeAttribute(url);
        }
        return result;
    }

    private List<Tree<FunctionMenuTreeAttribute>> createChildrenTree(Tree<FunctionMenuTreeAttribute> parent, List<Function> allFunctions) {
        List<Tree<FunctionMenuTreeAttribute>> children = allFunctions.stream()
                .filter(c -> parent.getId().equals(c.getParentId()))
                .sorted(Comparator.comparing(Function::getSorted))
                .map(createTree(allFunctions))
                .collect(Collectors.toList());
        return CollectionUtils.isEmpty(children) ? Collections.emptyList() : children;
    }

}
