package com.rex.my.web.controller;

import com.rex.my.web.controller.base.BaseControllerTest;
import org.junit.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ComboboxControllerTest extends BaseControllerTest {

    @Test
    public void getTradeTypes() throws Exception {
        sendGetJsonRequest("/combobox/trade/types")
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].text").value("收入"))
                .andExpect(jsonPath("$[1].text").value("支出"))
                .andExpect(jsonPath("$[2].text").value("轉帳"));

    }

    @Test
    public void getAccounts() throws Exception {
        sendGetJsonRequestWithUserSession("/combobox/accounts")
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0].text").value("玉山"))
                .andExpect(jsonPath("$[1].text").value("中國信託"))
                .andExpect(jsonPath("$[2].text").value("郵局"))
                .andExpect(jsonPath("$[3].text").value("現金"))
                .andExpect(jsonPath("$[4].text").value("永豐信用卡"));
    }

    @Test
    public void getItems() throws Exception {
        sendGetJsonRequestWithUserSession("/combobox/items")
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].text").value("用餐"))
                .andExpect(jsonPath("$[1].text").value("睡覺"))
                .andExpect(jsonPath("$[2].text").value("大便"));
    }

}
