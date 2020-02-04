package com.rex.practice.model.easyui.grid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rex.practice.model.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountGridVo extends BaseModel {

    private String id;
    private String name;
    private BigDecimal money;
    private String account_type_id;

}
