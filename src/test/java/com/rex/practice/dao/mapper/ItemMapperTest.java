package com.rex.practice.dao.mapper;

import com.rex.practice.dao.mapper.base.BaseMapperTest;
import com.rex.practice.dao.model.Item;
import com.rex.practice.model.easyui.grid.ItemGridVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@Sql({"/db/data/data-dev-user.sql", "/db/data/data-dev-item.sql"})
public class ItemMapperTest extends BaseMapperTest {

    @Autowired
    private ItemMapper mapper;

    @Test
    public void insertSelective() {
        Date today = new Date();
        Item entity = new Item();
        entity.setName("測試類別");
        entity.setUserId("a");
        entity.setRemoved(Boolean.TRUE);
        entity.setCreateDate(today);
        entity.setUpdateDate(today);
        int executeCount = mapper.insertSelective(entity);
        logger.info("id: {}", entity.getId());
        assertFalse(StringUtils.isEmpty(entity.getId()));
        assertEquals(32, entity.getId().length());
        assertEquals(1, executeCount);
    }

    @Test
    public void selectAll() {
        assertEquals(3, mapper.selectAll("a").size());
    }

    @Test
    public void selectByPrimaryKey() {
        assertEquals("a", mapper.selectByPrimaryKey("a").getId());
    }

    @Test
    public void updateSelectiveByPrimaryKey() {
        Item entity = mapper.selectByPrimaryKey("a");
        entity.setUpdateDate(new Date());
        int executeCount = mapper.updateSelectiveByPrimaryKey(entity);
        assertEquals(1, executeCount);
    }

    @Test
    public void selectForGrid() {
        List<ItemGridVo> voList = mapper.selectForGrid("a");
        assertEquals(3, voList.size());
        assertEquals("a", voList.get(0).getId());
        assertEquals("用餐", voList.get(0).getName());
    }

    @Test
    public void deleteByIdsContainErrorId() {
        assertNotEquals(3, mapper.updateToDeleteByIds(new String[]{"a", "b", "e"}, "a"));
    }

    @Test
    public void deleteByIds() {
        assertEquals(2, mapper.updateToDeleteByIds(new String[]{"a", "b"}, "a"));
    }

}
