package com.rex.my.model.vo;

import com.rex.my.model.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login extends BaseModel {

    private String email;
    private String password;
    private Boolean isRemember = Boolean.FALSE;

}
