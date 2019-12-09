package com.rex.my.model.easyui;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rex.my.model.base.BaseModel;
import com.rex.my.model.easyui.base.TreeAttribute;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tree<A extends TreeAttribute> extends BaseModel {

    private String id;
    private String text;
    private String state = "open";
    private Boolean checked = Boolean.FALSE;
    private List<A> attributes;
    private List<Tree<A>> children;
    private String iconCls;

}
