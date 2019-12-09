package com.rex.my.model.easyui.grid;

import com.rex.my.model.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GridPagination extends BaseModel {

    private Integer page;
    private Integer rows;
    private String sort;
    private String order;

}
