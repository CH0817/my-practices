package com.rex.my.dao.mapper.primary;

import com.rex.my.model.dao.primary.Function;

import java.util.List;

public interface FunctionMapper {

    List<Function> findFunctions(String userId);

}
