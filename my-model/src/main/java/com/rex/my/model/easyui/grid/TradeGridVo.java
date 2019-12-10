package com.rex.my.model.easyui.grid;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rex.my.model.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TradeGridVo extends BaseModel {

    private String id;
    private String accountId;
    private String itemId;
    private BigDecimal money;
    private String tradeType;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date tradeDate;

}
