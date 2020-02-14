package com.rex.practice.dao.mapper;

import com.rex.practice.dao.model.Functions;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FunctionsMapper {

    List<Functions> findFunctions(String userId);

}
