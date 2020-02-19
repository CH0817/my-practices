package com.rex.practice.model.message.base;

import com.rex.practice.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class Message extends BaseModel {

    private String message;
    private String redirectUrl;
    private String icon = "";

}
