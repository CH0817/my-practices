package com.rex.my.business.service;

import com.rex.my.business.service.base.BaseServiceTest;
import com.rex.my.model.dao.primary.Trade;
import com.rex.my.model.input.SaveTrade;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TradeServiceTest extends BaseServiceTest {

    @Autowired
    private TradeService service;

    @Test
    public void save() {
        SaveTrade entity = new SaveTrade();
        entity.setMoney(new BigDecimal(1000));
        entity.setTrade_type("2");
        entity.setTrade_date(new Date());
        entity.setAccount_id("b");
        entity.setItem_id("b");

        when(tradeMapper.insertSelective(any(Trade.class))).thenReturn(1);

        service.save(entity);

        verify(tradeMapper, times(1)).insertSelective(any(Trade.class));
    }

}
