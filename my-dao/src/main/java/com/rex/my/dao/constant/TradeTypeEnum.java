package com.rex.my.dao.constant;

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
}
