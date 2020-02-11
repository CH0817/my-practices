package com.rex.practice.model.input;

import com.rex.practice.dao.model.Trade;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class SaveTrade extends Trade {

    @NotNull(message = "金額不能為空")
    private BigDecimal money;
    @NotBlank(message = "類型不能為空")
    private String trade_type;
    @NotNull(message = "交易日期不能為空")
    private Date trade_date;
    @NotBlank(message = "帳戶不能為空")
    private String account_id;
    @NotBlank(message = "項目不能為空")
    private String item_id;

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
        setTradeType(trade_type);
    }

    public void setTrade_date(Date trade_date) {
        this.trade_date = trade_date;
        setTradeDate(trade_date);
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
        setAccountId(account_id);
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
        setItemId(item_id);
    }

}
