package com.rex.practice.model.input;

import com.rex.practice.dao.model.primary.Item;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SaveItem extends Item {

    @NotBlank(message = "名稱不能為空")
    private String name;

}
