package com.rex.practice.model.easyui.grid;

import com.rex.practice.model.base.BaseModel;
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
