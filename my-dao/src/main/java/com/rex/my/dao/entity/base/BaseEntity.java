package com.rex.my.dao.entity.base;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class BaseEntity implements Serializable {

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(ToStringStyle.MULTI_LINE_STYLE);
    }

}
