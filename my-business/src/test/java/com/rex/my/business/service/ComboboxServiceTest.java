package com.rex.my.business.service;

import com.rex.my.business.service.base.BaseServiceTest;
import com.rex.my.model.easyui.combobox.ComboboxData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ComboboxServiceTest extends BaseServiceTest {

    @Autowired
    private ComboboxService service;

    @Test
    public void getTradeTypeComboboxData() {
        String[] names = {"收入", "支出", "轉帳"};
        List<ComboboxData> result = service.getTradeTypeComboboxData();
        assertEquals(names.length, result.size());
        Stream.of(names).forEach(c -> {
            boolean isMatch = result.stream().anyMatch(t -> c.equals(t.getText()));
            assertTrue("combobox data 未包含" + c, isMatch);
        });
    }

    @Test
    public void getAccountComboboxData() {
        when(accountMapper.selectAll("a")).thenReturn(Collections.emptyList());
        service.getAccountComboboxData("a");
        verify(accountMapper, times(1)).selectAll("a");
    }

    @Test
    public void getItemComboboxData() {
        when(itemMapper.selectAll("a")).thenReturn(Collections.emptyList());
        service.getItemComboboxData("a");
        verify(itemMapper, times(1)).selectAll("a");
    }

}
