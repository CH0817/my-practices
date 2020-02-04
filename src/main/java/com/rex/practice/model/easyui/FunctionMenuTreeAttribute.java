package com.rex.practice.model.easyui;

import com.rex.practice.model.base.BaseModel;
import com.rex.practice.model.easyui.base.TreeAttribute;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FunctionMenuTreeAttribute extends BaseModel implements TreeAttribute {

    private String url;

    public FunctionMenuTreeAttribute(String url) {
        this.url = url;
    }

}
