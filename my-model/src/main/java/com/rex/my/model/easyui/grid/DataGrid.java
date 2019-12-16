package com.rex.my.model.easyui.grid;

import com.github.pagehelper.PageInfo;
import com.rex.my.model.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class DataGrid<R> extends BaseModel {

    private Long total;
    private List<R> rows;

    public DataGrid(PageInfo<R> pageInfo) {
        assert Objects.nonNull(pageInfo);
        this.total = pageInfo.getTotal();
        this.rows = pageInfo.getList();
    }

    public DataGrid(Long total, List<R> rows) {
        this.total = total;
        this.rows = rows;
    }

}
