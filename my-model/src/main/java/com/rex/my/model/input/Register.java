package com.rex.my.model.input;

import com.rex.my.model.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
public class Register extends BaseModel {

    @NotBlank(message = "Email不能為空")
    @Email(message = "錯誤的Email格式")
    private String email;
    @NotBlank(message = "密碼不能為空")
    @Size(min = 8, max = 12, message = "密碼必須是8~12碼")
    private String password;
    @NotBlank(message = "確認密碼不能為空")
    private String confirmPassword;

}
