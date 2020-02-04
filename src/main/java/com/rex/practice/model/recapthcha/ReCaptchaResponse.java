package com.rex.practice.model.recapthcha;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rex.practice.model.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReCaptchaResponse extends BaseModel {

    private Boolean success;
    @JsonProperty("challenge_ts")
    private Date challengeTs;
    private String hostname;

}
