package com.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.SalaryItemDao;
import com.hr.entity.SalaryItem;
import com.hr.service.SalaryItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryItemServiceImpl extends ServiceImpl<SalaryItemDao, SalaryItem> implements SalaryItemService {

    @Override
    public List<SalaryItem> getItemsByStructureId(Long structureId) {
        LambdaQueryWrapper<SalaryItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SalaryItem::getStructureId, structureId);
        queryWrapper.eq(SalaryItem::getStatus, 1);
        queryWrapper.orderByAsc(SalaryItem::getSort);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<SalaryItem> getItemsByTenantId(Long tenantId) {
        LambdaQueryWrapper<SalaryItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SalaryItem::getTenantId, tenantId);
        queryWrapper.eq(SalaryItem::getStatus, 1);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public SalaryItem getItemById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean addItem(SalaryItem item) {
        return save(item);
    }

    @Override
    public boolean updateItem(SalaryItem item) {
        return updateById(item);
    }

    @Override
    public boolean deleteItem(Long id) {
        return removeById(id);
    }
}