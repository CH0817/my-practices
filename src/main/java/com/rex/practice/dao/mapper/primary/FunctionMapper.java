package com.rex.practice.dao.mapper.primary;

import com.rex.practice.dao.model.primary.Function;

import java.util.List;

public interface FunctionMapper {

    List<Function> findFunctions(String userId);

}
