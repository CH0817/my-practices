package com.rex.my.business.service;

import com.rex.my.business.service.base.BaseServiceTest;
import com.rex.my.model.easyui.combobox.ComboboxData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ComboboxServiceTest extends BaseServiceTest {

    @Autowired
    private ComboboxService service;

    @Test
    public void getTradeTypeComboboxData() {
        verifyComboboxData(service.getTradeTypeComboboxData(), "收入", "支出", "轉帳");
    }

    @Test
    public void getAccountComboboxData() {
        verifyComboboxData(service.getAccountComboboxData(), "玉山", "中國信託", "郵局", "現金", "永豐信用卡");
    }

    @Test
    public void getItemComboboxData() {
        verifyComboboxData(service.getItemComboboxData(), "用餐", "睡覺", "大便");
    }

    private void verifyComboboxData(List<ComboboxData> dataList, String... contains) {
        assertEquals(contains.length, dataList.size());
        Stream.of(contains).forEach(c -> {
            boolean isMatch = dataList.stream().anyMatch(t -> c.equals(t.getText()));
            assertTrue("combobox data 未包含" + c, isMatch);
        });
    }

}
