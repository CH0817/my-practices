package com.rex.practice.model.recapthcha;

import com.rex.practice.model.base.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "recaptcha")
public class ReCaptchaProperty extends BaseModel {

    private String verifyUrl;
    private String v2Checkbox;
    private String v2Invisible;
    private String v3;

}
