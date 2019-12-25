package com.rex.my.model.input;

import com.rex.my.model.dao.primary.Account;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Setter
@Getter
public class SaveAccount extends Account {

    @NotBlank(message = "名稱不能為空")
    private String name;
    private BigDecimal money = new BigDecimal(0);
    @NotBlank(message = "帳戶類型不能為空")
    private String account_type_id;

    @Override
    public String getAccountTypeId() {
        return account_type_id;
    }

}
