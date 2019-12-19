package com.rex.my.web.controller;

import com.rex.my.web.controller.base.BaseControllerTest;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ComboboxControllerTest extends BaseControllerTest {

    @Test
    public void getTradeTypes() throws Exception {
        sendGetJsonRequest("/combobox/trade/types").andExpect(status().isOk());
        verify(comboboxService, times(1)).getTradeTypeComboboxData();
    }

    @Test
    public void getAccounts() throws Exception {
        sendGetJsonRequest("/combobox/accounts").andExpect(status().isOk());
        verify(comboboxService, times(1)).getAccountComboboxData(eq("a"));
    }

    @Test
    public void getItems() throws Exception {
        sendGetJsonRequest("/combobox/items").andExpect(status().isOk());
        verify(comboboxService, times(1)).getItemComboboxData(eq("a"));
    }

}
