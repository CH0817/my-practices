package com.rex.practice.model.verify;

import com.rex.practice.model.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterVerifyError extends BaseModel {

    private boolean isAccountError;
    private boolean isTokenError;

}
