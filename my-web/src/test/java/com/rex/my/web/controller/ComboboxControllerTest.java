package com.rex.my.web.controller;

import com.rex.my.business.service.ComboboxService;
import com.rex.my.model.dao.primary.User;
import com.rex.my.model.easyui.combobox.ComboboxData;
import com.rex.my.web.constant.SessionAttribute;
import com.rex.my.web.controller.base.BaseControllerTest;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = {ComboboxController.class})
public class ComboboxControllerTest extends BaseControllerTest {

    @MockBean
    private ComboboxService service;

    @Test
    public void getTradeTypes() throws Exception {
        String[] data = {"收入", "支出", "轉帳"};
        when(service.getTradeTypeComboboxData()).thenReturn(createComboboxDataList(data));
        expectResult(sendGetJsonRequest("/combobox/trade/types"), data);
    }

    @Test
    public void getAccounts() throws Exception {
        String[] data = {"玉山", "中國信託", "郵局", "現金", "永豐信用卡"};
        when(service.getAccountComboboxData(((User) session.getAttribute(SessionAttribute.USER)).getId()))
                .thenReturn(createComboboxDataList(data));
        expectResult(sendGetJsonRequestWithUserSession("/combobox/accounts"), data);
    }

    @Test
    public void getItems() throws Exception {
        String[] data = {"用餐", "睡覺", "大便"};
        when(service.getItemComboboxData(((User) session.getAttribute(SessionAttribute.USER)).getId()))
                .thenReturn(createComboboxDataList(data));
        expectResult(sendGetJsonRequestWithUserSession("/combobox/items"), data);
    }

    private List<ComboboxData> createComboboxDataList(String... text) {
        assert Objects.nonNull(text);
        return Stream.of(text).map(t -> {
            ComboboxData comboboxData = new ComboboxData();
            comboboxData.setText(t);
            return comboboxData;
        }).collect(Collectors.toList());
    }

    private void expectResult(ResultActions resultActions, String[] data) throws Exception {
        resultActions.andExpect(jsonPath("$.length()").value(data.length));
        IntStream.range(0, data.length).forEach(i -> {
            try {
                resultActions.andExpect(jsonPath("$[" + i + "].text").value(data[i]));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
