package com.rex.my.business.service;

import com.github.pagehelper.PageInfo;
import com.rex.my.business.service.base.BaseServiceTest;
import com.rex.my.dao.mapper.primary.AccountMapper;
import com.rex.my.dao.mapper.primary.ItemMapper;
import com.rex.my.model.easyui.grid.AccountGridVo;
import com.rex.my.model.easyui.grid.GridPagination;
import com.rex.my.model.easyui.grid.ItemGridVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ItemServiceTest extends BaseServiceTest {

    @MockBean
    protected ItemMapper itemMapper;
    @Autowired
    private ItemService service;

    @Test
    public void selectForGrid() {
        String userId = "a";

        List<ItemGridVo> returnAccounts = new ArrayList<>();

        ItemGridVo vo = new ItemGridVo();
        vo.setId("d");
        vo.setName("吃飯");

        returnAccounts.add(vo);

        when(itemMapper.selectForGrid(userId)).thenReturn(returnAccounts);

        GridPagination pagination = new GridPagination();
        pagination.setPage(1);
        pagination.setRows(30);
        pagination.setSort("money");
        pagination.setOrder("desc");

        PageInfo<ItemGridVo> result = service.getItemsForGrid(pagination, "a");

        verify(itemMapper, atLeast(1)).selectForGrid(anyString());

        assertEquals(1, result.getTotal());
        assertEquals("id 不相同", vo.getId(), result.getList().get(0).getId());
        assertEquals("name 不相同", vo.getName(), result.getList().get(0).getName());
    }

}
