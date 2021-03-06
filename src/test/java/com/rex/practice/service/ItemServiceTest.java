package com.rex.practice.service;

import com.github.pagehelper.PageInfo;
import com.rex.practice.dao.model.Item;
import com.rex.practice.model.easyui.grid.GridPagination;
import com.rex.practice.model.easyui.grid.ItemGridVo;
import com.rex.practice.model.input.SaveItem;
import com.rex.practice.model.input.UpdateItem;
import com.rex.practice.service.base.BaseServiceTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ItemServiceTest extends BaseServiceTest {

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

    @Test(expected = ConstraintViolationException.class)
    @Ignore
    @Deprecated
    public void saveWithNoParams() {
        service.save(null);
    }

    @Test
    public void save() {
        SaveItem entity = new SaveItem();
        entity.setName("測試");
        entity.setUserId("a");
        when(itemMapper.insertSelective(any(Item.class))).thenReturn(1);
        service.save(entity);
        verify(itemMapper, times(1)).insertSelective(any(Item.class));
    }

    @Test(expected = ConstraintViolationException.class)
    @Ignore
    @Deprecated
    public void deleteWithNoParams() {
        service.updateToDeleteByIds(new String[]{}, "");
    }

    @Test
    public void deleteByIds() {
        when(itemMapper.updateToDeleteByIds(any(String[].class), eq(userId))).thenReturn(3);
        service.updateToDeleteByIds(new String[]{"a", "b"}, userId);
        verify(itemMapper, times(1)).updateToDeleteByIds(any(String[].class), eq(userId));
    }

    @Test
    public void updateById() {
        when(itemMapper.updateSelectiveByPrimaryKey(any(UpdateItem.class))).thenReturn(1);
        service.updateById(new Item());
        verify(itemMapper, times(1)).updateSelectiveByPrimaryKey(any(Item.class));
    }

}
