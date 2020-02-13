package com.rex.practice.dao.mapper;

import com.rex.practice.dao.mapper.base.BaseMapperTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.Assert.assertEquals;

@Sql({"/db/data/data-dev-user.sql", "/db/data/data-dev-functions.sql", "/db/data/data-dev-user_functions.sql"})
public class FunctionMapperTest extends BaseMapperTest {

    @Autowired
    private FunctionsMapper mapper;

    @Test
    public void selectAll() {
        assertEquals(8, mapper.findFunctions("a").size());
    }

}
