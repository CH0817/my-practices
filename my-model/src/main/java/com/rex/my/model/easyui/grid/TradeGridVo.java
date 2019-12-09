package com.rex.my.model.easyui.grid;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rex.my.model.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
public class TradeGridVo extends BaseModel {

    private String id;
    private String accountId;
    private String itemId;
    @JsonIgnore
    private BigDecimal money;
    private String moneyString;
    private String tradeType;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date tradeDate;
    private String accountName;
    private String itemName;

    public String getMoneyString() {
        return Objects.nonNull(money) ? money.toString() : "";
    }

}
