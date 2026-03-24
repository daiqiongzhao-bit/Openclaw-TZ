package com.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.WelfareTypeDao;
import com.hr.entity.WelfareType;
import com.hr.service.WelfareTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WelfareTypeServiceImpl extends ServiceImpl<WelfareTypeDao, WelfareType> implements WelfareTypeService {

    @Override
    public List<WelfareType> getTypesByTenantId(Long tenantId) {
        LambdaQueryWrapper<WelfareType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WelfareType::getTenantId, tenantId);
        queryWrapper.eq(WelfareType::getStatus, 1);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public WelfareType getTypeById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean addType(WelfareType type) {
        return save(type);
    }

    @Override
    public boolean updateType(WelfareType type) {
        return updateById(type);
    }

    @Override
    public boolean deleteType(Long id) {
        return removeById(id);
    }
}