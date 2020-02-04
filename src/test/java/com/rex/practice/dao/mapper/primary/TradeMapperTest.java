package com.rex.practice.dao.mapper.primary;

import com.rex.practice.constant.TradeTypeEnum;
import com.rex.practice.dao.mapper.primary.base.BaseMapperTest;
import com.rex.practice.dao.model.primary.Trade;
import com.rex.practice.model.input.SaveTrade;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TradeMapperTest extends BaseMapperTest {

    @Autowired
    private TradeMapper mapper;

    @Test
    public void insertSelective() {
        Date today = new Date();
        Trade entity = new Trade();
        entity.setUserId("a");
        entity.setAccountId("a");
        entity.setItemId("a");
        entity.setMoney(new BigDecimal(100));
        entity.setTradeType(TradeTypeEnum.INCOME.getCode());
        entity.setTradeDate(today);
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
    public void insertSelectiveSaveTrade() {
        Date today = new Date();
        SaveTrade entity = new SaveTrade();
        entity.setUserId("a");
        entity.setAccount_id("a");
        entity.setItem_id("a");
        entity.setMoney(new BigDecimal(100));
        entity.setTrade_type(TradeTypeEnum.INCOME.getCode());
        entity.setTrade_date(today);
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
        assertEquals(66, mapper.selectAll("a").size());
    }

    @Test
    public void selectByPrimaryKey() {
        assertEquals("a", mapper.selectByPrimaryKey("a").getId());
    }

    @Test
    public void update2DeleteByPrimaryKey() {
        assertEquals(1, mapper.update2DeleteByPrimaryKey("a"));
    }

    @Test
    public void updateSelectiveByPrimaryKey() {
        Trade entity = mapper.selectByPrimaryKey("a");
        entity.setUpdateDate(new Date());
        int executeCount = mapper.updateSelectiveByPrimaryKey(entity);
        assertEquals(1, executeCount);
    }

}
