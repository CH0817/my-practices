package com.rex.practice.model.help;

import com.rex.practice.model.base.BaseModel;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Form extends BaseModel {

    @NonNull
    private String action;
    private String method = "get";
    private Map<String, Object> parameter;

}
