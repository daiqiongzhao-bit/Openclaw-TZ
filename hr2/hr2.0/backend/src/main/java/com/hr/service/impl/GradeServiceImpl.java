package com.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.GradeDao;
import com.hr.entity.Grade;
import com.hr.service.GradeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeServiceImpl extends ServiceImpl<GradeDao, Grade> implements GradeService {

    @Override
    public List<Grade> getGradesByTenantId(Long tenantId) {
        LambdaQueryWrapper<Grade> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Grade::getTenantId, tenantId);
        queryWrapper.eq(Grade::getStatus, 1);
        queryWrapper.orderByAsc(Grade::getLevel);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Grade getGradeById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean addGrade(Grade grade) {
        return save(grade);
    }

    @Override
    public boolean updateGrade(Grade grade) {
        return updateById(grade);
    }

    @Override
    public boolean deleteGrade(Long id) {
        return removeById(id);
    }
}