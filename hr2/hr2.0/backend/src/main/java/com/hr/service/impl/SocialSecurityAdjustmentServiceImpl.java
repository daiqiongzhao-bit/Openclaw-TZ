package com.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.SocialSecurityAdjustmentDao;
import com.hr.entity.SocialSecurityAdjustment;
import com.hr.service.SocialSecurityAdjustmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SocialSecurityAdjustmentServiceImpl extends ServiceImpl<SocialSecurityAdjustmentDao, SocialSecurityAdjustment> implements SocialSecurityAdjustmentService {

    @Override
    public boolean applyAdjustment(SocialSecurityAdjustment adjustment) {
        adjustment.setTenantId(1L); // 假设当前租户ID为1
        adjustment.setStatus(1); // 1-待审批
        adjustment.setCreateTime(LocalDateTime.now());
        adjustment.setUpdateTime(LocalDateTime.now());
        return save(adjustment);
    }

    @Override
    public List<SocialSecurityAdjustment> getAdjustmentsByUserId(Long userId) {
        LambdaQueryWrapper<SocialSecurityAdjustment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SocialSecurityAdjustment::getUserId, userId);
        queryWrapper.orderByDesc(SocialSecurityAdjustment::getEffectiveDate);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<SocialSecurityAdjustment> getAdjustmentsByDateRange(LocalDate startDate, LocalDate endDate) {
        LambdaQueryWrapper<SocialSecurityAdjustment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(SocialSecurityAdjustment::getEffectiveDate, startDate, endDate);
        queryWrapper.orderByDesc(SocialSecurityAdjustment::getEffectiveDate);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public SocialSecurityAdjustment getAdjustmentById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean approveAdjustment(Long id, Integer status) {
        SocialSecurityAdjustment adjustment = baseMapper.selectById(id);
        if (adjustment != null) {
            adjustment.setStatus(status); // 2-已批准
            adjustment.setUpdateTime(LocalDateTime.now());
            return updateById(adjustment);
        }
        return false;
    }

    @Override
    public boolean rejectAdjustment(Long id) {
        SocialSecurityAdjustment adjustment = baseMapper.selectById(id);
        if (adjustment != null) {
            adjustment.setStatus(3); // 3-已拒绝
            adjustment.setUpdateTime(LocalDateTime.now());
            return updateById(adjustment);
        }
        return false;
    }
}