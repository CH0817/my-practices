package com.rex.practice.model.help;

import com.rex.practice.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ConfirmParameter extends BaseModel {

    private String title;
    private String message;
    private Form confirmForm;
    private Form denyForm;

}
