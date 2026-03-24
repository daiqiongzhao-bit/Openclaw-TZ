package com.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.HolidayTypeDao;
import com.hr.entity.HolidayType;
import com.hr.service.HolidayTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayTypeServiceImpl extends ServiceImpl<HolidayTypeDao, HolidayType> implements HolidayTypeService {

    @Override
    public List<HolidayType> getTypesByTenantId(Long tenantId) {
        LambdaQueryWrapper<HolidayType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HolidayType::getTenantId, tenantId);
        queryWrapper.eq(HolidayType::getStatus, 1);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public HolidayType getTypeById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean addType(HolidayType type) {
        return save(type);
    }

    @Override
    public boolean updateType(HolidayType type) {
        return updateById(type);
    }

    @Override
    public boolean deleteType(Long id) {
        return removeById(id);
    }
}