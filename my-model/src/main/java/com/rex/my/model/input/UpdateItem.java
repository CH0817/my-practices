package com.rex.my.model.input;

import com.rex.my.model.dao.primary.Item;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UpdateItem extends Item {

    @NotBlank(message = "ID不能為空")
    private String id;
    @NotBlank(message = "名稱不能為空")
    private String name;

}
