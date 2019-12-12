package com.rex.my.dao.mapper.base;

import java.util.List;

public interface BaseMapper<E> {

    int insertSelective(E entity);

    List<E> selectAll(String userId);

    E selectByPrimaryKey(String id);

    int update2DeleteByPrimaryKey(String id);

    int updateSelectiveByPrimaryKey(E entity);

}
