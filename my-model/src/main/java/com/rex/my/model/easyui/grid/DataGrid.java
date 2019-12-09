package com.rex.my.model.easyui.grid;

import com.rex.my.model.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DataGrid<R> extends BaseModel {

    private Integer total;
    private List<R> rows;

    public DataGrid(Integer total, List<R> rows) {
        this.total = total;
        this.rows = rows;
    }

}
