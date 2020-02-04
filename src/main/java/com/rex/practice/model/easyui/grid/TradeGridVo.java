package com.rex.practice.model.easyui.grid;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rex.practice.model.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TradeGridVo extends BaseModel {

    private String id;
    private String account_id;
    private String item_id;
    private BigDecimal money;
    private String trade_type;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date trade_date;

}
