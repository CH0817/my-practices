package com.rex.my.web.controller;

import com.github.pagehelper.PageInfo;
import com.rex.my.model.easyui.grid.GridPagination;
import com.rex.my.web.controller.base.BaseControllerTest;
import org.junit.Test;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    public void saveWithNoParams() throws Exception {
        mvc.perform(post("/item/save").with(csrf()))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").value("名稱不能為空"));
    }

    @Test
    public void save() throws Exception {
        String userId = "a";
        String name = "測試";
        when(itemService.save(eq(name), eq(userId))).thenReturn(UUID.randomUUID().toString().replace("-", ""));
        mvc.perform(post("/item/save").with(csrf()).param("name", name))
                .andDo(print())
                .andExpect(status().isOk());
        verify(itemService, times(1)).save(name, userId);
    }

}
