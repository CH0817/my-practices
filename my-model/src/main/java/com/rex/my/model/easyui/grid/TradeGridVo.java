package com.rex.my.model.easyui.grid;

import com.rex.my.model.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeGridVo extends BaseModel {

    private String id;
    private String accountId;
    private String itemId;
    private String money;
    private String tradeType;
    private String tradeDate;
    private String accountName;
    private String itemName;

}
