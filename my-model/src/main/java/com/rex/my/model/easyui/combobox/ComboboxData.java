package com.rex.my.model.easyui.combobox;

import com.rex.my.model.base.BaseModel;
import com.rex.my.model.constant.TradeTypeEnum;
import com.rex.my.model.dao.primary.Account;
import com.rex.my.model.dao.primary.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComboboxData extends BaseModel {

    private String value;
    private String text;

    public ComboboxData() {}

    public ComboboxData(TradeTypeEnum tradeTypeEnum) {
        this.value = tradeTypeEnum.getCode();
        this.text = tradeTypeEnum.getDescription();
    }

    public ComboboxData(Account account) {
        this.value = account.getId();
        this.text = account.getName();
    }

    public ComboboxData(Item item) {
        this.value = item.getId();
        this.text = item.getName();
    }

}
