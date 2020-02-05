package com.rex.practice.dao.mapper;

import com.rex.practice.dao.model.primary.Function;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FunctionMapper {

    List<Function> findFunctions(String userId);

}
