package com.rex.practice.dao.mapper;

import com.rex.practice.dao.mapper.base.BaseMapperTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class FunctionMapperTest extends BaseMapperTest {

    @Autowired
    private FunctionMapper mapper;

    @Test
    public void selectAll() {
        assertEquals(8, mapper.findFunctions("a").size());
    }

}
