package com.rex.my.web.controller;

import com.github.pagehelper.PageInfo;
import com.rex.my.business.service.ItemService;
import com.rex.my.model.easyui.grid.GridPagination;
import com.rex.my.model.easyui.grid.ItemGridVo;
import com.rex.my.web.controller.base.BaseControllerTest;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ItemController.class)
public class ItemControllerTest extends BaseControllerTest {

    @MockBean
    private ItemService service;

    @Test
    public void content() throws Exception {
        sendGetJsonRequest("/item/content")
                .andExpect(status().isOk())
                .andExpect(view().name("page/content/item"));
    }

    @Test
    public void list() throws Exception {
        PageInfo<ItemGridVo> pageInfo = new PageInfo<>();
        pageInfo.setTotal(1);

        ItemGridVo vo = new ItemGridVo();
        vo.setId("a");
        vo.setName("吃飯");

        pageInfo.setList(Collections.singletonList(vo));

        when(service.getItemsForGrid(any(GridPagination.class), anyString())).thenReturn(pageInfo);

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("page", "1");
        paramMap.put("rows", "30");

        sendPostJsonRequestWithUserSession("/item/list", paramMap)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.rows[0].id").value(vo.getId()))
                .andExpect(jsonPath("$.rows[0].name").value(vo.getName()));
    }

}
