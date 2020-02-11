package com.rex.practice.model.easyui;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rex.practice.dao.model.Function;
import com.rex.practice.model.base.BaseModel;
import com.rex.practice.model.easyui.base.TreeAttribute;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tree<A extends TreeAttribute> extends BaseModel {

    private String id;
    private String text;
    private String state = "open";
    private Boolean checked = Boolean.FALSE;
    private A attributes;
    private List<Tree<A>> children = Collections.emptyList();
    private String iconCls;

    public Tree() {}

    public Tree(Function function) {
        this.id = function.getId();
        this.text = function.getName();
        this.iconCls = function.getIcon();
    }

}
