package com.rex.my.model.easyui.grid;

import com.rex.my.model.base.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class GridPagination extends BaseModel {

    private Integer page;
    private Integer rows;
    private String sort;
    private String order;
    private String offset;
    private String orderBy;

    public int getOffset() {
        int result = 0;
        if (page != 1) {
            result = (page - 1) * rows;
        }
        return result;
    }

    public String getOrderBy() {
        if (StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(order)) {
            orderBy = sort + " " + order;
        }
        return orderBy;
    }

}
