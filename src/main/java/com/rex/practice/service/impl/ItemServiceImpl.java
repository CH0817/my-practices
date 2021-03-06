package com.rex.practice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rex.practice.dao.mapper.ItemMapper;
import com.rex.practice.dao.model.Item;
import com.rex.practice.model.easyui.grid.GridPagination;
import com.rex.practice.model.easyui.grid.ItemGridVo;
import com.rex.practice.service.ItemService;
import com.rex.practice.service.base.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ItemServiceImpl extends BaseServiceImpl implements ItemService {

    private ItemMapper mapper;

    @Autowired
    public ItemServiceImpl(ItemMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public PageInfo<ItemGridVo> getItemsForGrid(GridPagination pagination, String userId) {
        PageHelper.startPage(pagination.getPage(), pagination.getRows());
        if (StringUtils.isNotBlank(pagination.getSort()) && StringUtils.isNotBlank(pagination.getOrder())) {
            PageHelper.orderBy(pagination.getSort() + " " + pagination.getOrder());
        }
        return new PageInfo<>(mapper.selectForGrid(userId));
    }

    @Override
    public <E extends Item> String save(E entity) {
        entity.setCreateDate(new Date());
        return mapper.insertSelective(entity) == 1 ? entity.getId() : "";
    }

    @Override
    public boolean updateToDeleteByIds(String[] ids, String userId) {
        return ids.length == mapper.updateToDeleteByIds(ids, userId);
    }

    @Override
    public <E extends Item> Boolean updateById(E entity) {
        entity.setUpdateDate(new Date());
        return 1 == mapper.updateSelectiveByPrimaryKey(entity);
    }

}
