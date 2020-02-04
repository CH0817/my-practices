package com.rex.practice.model.vo;

import com.rex.practice.model.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class Login extends BaseModel {

    @NotBlank(message = "Email不能為空")
    @Email(message = "Email格式錯誤")
    private String email;
    @Size(message = "密碼長度必須為8~12", min = 8, max = 12)
    private String password;
    private Boolean isRemember = Boolean.FALSE;

}
