package com.rex.practice.model.verify;

import com.rex.practice.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterError extends BaseModel {

    private String errorMessage;
    private String viewName;

}
