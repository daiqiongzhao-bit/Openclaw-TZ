package com.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.SalaryStructureDao;
import com.hr.entity.SalaryStructure;
import com.hr.service.SalaryStructureService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryStructureServiceImpl extends ServiceImpl<SalaryStructureDao, SalaryStructure> implements SalaryStructureService {

    @Override
    public List<SalaryStructure> getStructuresByTenantId(Long tenantId) {
        LambdaQueryWrapper<SalaryStructure> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SalaryStructure::getTenantId, tenantId);
        queryWrapper.eq(SalaryStructure::getStatus, 1);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public SalaryStructure getStructureById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean addStructure(SalaryStructure structure) {
        return save(structure);
    }

    @Override
    public boolean updateStructure(SalaryStructure structure) {
        return updateById(structure);
    }

    @Override
    public boolean deleteStructure(Long id) {
        return removeById(id);
    }
}