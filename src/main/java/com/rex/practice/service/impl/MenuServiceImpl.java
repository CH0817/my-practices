package com.rex.practice.service.impl;

import com.rex.practice.dao.mapper.FunctionsMapper;
import com.rex.practice.dao.model.Functions;
import com.rex.practice.model.easyui.FunctionMenuTreeAttribute;
import com.rex.practice.model.easyui.Tree;
import com.rex.practice.service.MenuService;
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

    private FunctionsMapper mapper;

    @Autowired
    public MenuServiceImpl(FunctionsMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Tree<FunctionMenuTreeAttribute>> getFunctionMenuTree(String userId) {
        List<Functions> functions = mapper.findFunctions(userId);
        List<Tree<FunctionMenuTreeAttribute>> result = functions.stream()
                .filter(f -> StringUtils.isBlank(f.getParentId()))
                .sorted(Comparator.comparing(Functions::getSorted))
                .map(createTree(functions))
                .collect(Collectors.toList());
        return result;
    }

    private java.util.function.Function<Functions, Tree<FunctionMenuTreeAttribute>> createTree(List<Functions> allFunctions) {
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

    private List<Tree<FunctionMenuTreeAttribute>> createChildrenTree(Tree<FunctionMenuTreeAttribute> parent, List<Functions> allFunctions) {
        List<Tree<FunctionMenuTreeAttribute>> children = allFunctions.stream()
                .filter(c -> parent.getId().equals(c.getParentId()))
                .sorted(Comparator.comparing(Functions::getSorted))
                .map(createTree(allFunctions))
                .collect(Collectors.toList());
        return CollectionUtils.isEmpty(children) ? Collections.emptyList() : children;
    }

}
