package com.rex.practice.model.easyui.combobox;

import com.rex.practice.constant.TradeTypeEnum;
import com.rex.practice.dao.model.Account;
import com.rex.practice.dao.model.AccountType;
import com.rex.practice.dao.model.Item;
import com.rex.practice.model.base.BaseModel;
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

    public ComboboxData(AccountType accountType) {
        this.value = accountType.getId();
        this.text = accountType.getName();
    }

}
