package com.rex.practice.dao.mapper.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseMapper<E> {

    int insertSelective(E entity);

    List<E> selectAll(String userId);

    E selectByPrimaryKey(String id);

    @Deprecated
    int update2DeleteByPrimaryKey(String id);

    int updateSelectiveByPrimaryKey(E entity);

    int updateToDeleteByIds(@Param("ids") String[] ids, @Param("userId") String userId);

}
