package com.rex.my.web.controller;

import com.github.pagehelper.PageInfo;
import com.rex.my.model.easyui.grid.GridPagination;
import com.rex.my.web.controller.base.BaseControllerTest;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ItemControllerTest extends BaseControllerTest {

    @Test
    public void content() throws Exception {
        sendGetRequest("/item/content").andExpect(view().name("page/content/item"));
    }

    @Test
    public void list() throws Exception {
        when(itemService.getItemsForGrid(any(GridPagination.class), eq("a"))).thenReturn(new PageInfo<>());
        sendPaginationPostJsonRequest("/item/list").andExpect(status().isOk());
        verify(itemService, times(1)).getItemsForGrid(any(GridPagination.class), eq("a"));
    }

}
