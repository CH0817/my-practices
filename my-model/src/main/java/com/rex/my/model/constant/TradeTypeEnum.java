package com.rex.my.model.constant;

import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

public enum TradeTypeEnum {
    INCOME("1", "收入"),
    EXPENSE("2", "支出"),
    TRANSFER_ACCOUNT("3", "轉帳");

    private String code;
    private String description;

    TradeTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static String getDescriptionByCode(String code) {
        assert StringUtils.isNotBlank(code);
        return Stream.of(TradeTypeEnum.values())
                .filter(type -> type.getCode().equals(code))
                .map(TradeTypeEnum::getDescription)
                .findFirst()
                .orElse("");
    }
}
