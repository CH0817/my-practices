package com.rex.my.web.controller;

import com.rex.my.web.controller.base.BaseControllerTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ComboboxControllerTest extends BaseControllerTest {

    @Test
    public void getTradeTypes() throws Exception {
        mvc.perform(get("/combobox/trade/types").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].text").value("收入"))
                .andExpect(jsonPath("$[1].text").value("支出"))
                .andExpect(jsonPath("$[2].text").value("轉帳"));

    }

    @Test
    public void getAccounts() throws Exception {
        mvc.perform(get("/combobox/accounts")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0].text").value("玉山"))
                .andExpect(jsonPath("$[1].text").value("中國信託"))
                .andExpect(jsonPath("$[2].text").value("郵局"))
                .andExpect(jsonPath("$[3].text").value("現金"))
                .andExpect(jsonPath("$[4].text").value("永豐信用卡"));
    }

    @Test
    public void getItems() throws Exception {
        mvc.perform(get("/combobox/items")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].text").value("用餐"))
                .andExpect(jsonPath("$[1].text").value("睡覺"))
                .andExpect(jsonPath("$[2].text").value("大便"));
    }

}
