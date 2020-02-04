package com.rex.my.web.model;

import com.rex.my.model.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReCaptchaResponse extends BaseModel {

    private Boolean success;
    private Date challenge_ts;
    private String hostname;

}
