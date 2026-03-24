package com.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.SocialSecurityChangeDao;
import com.hr.entity.SocialSecurityChange;
import com.hr.service.SocialSecurityChangeService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SocialSecurityChangeServiceImpl extends ServiceImpl<SocialSecurityChangeDao, SocialSecurityChange> implements SocialSecurityChangeService {

    @Override
    public boolean applyChange(SocialSecurityChange change) {
        change.setTenantId(1L); // 假设当前租户ID为1
        change.setStatus(1); // 1-待审批
        change.setCreateTime(LocalDateTime.now());
        change.setUpdateTime(LocalDateTime.now());
        return save(change);
    }

    @Override
    public List<SocialSecurityChange> getChangesByUserId(Long userId) {
        LambdaQueryWrapper<SocialSecurityChange> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SocialSecurityChange::getUserId, userId);
        queryWrapper.orderByDesc(SocialSecurityChange::getEffectiveDate);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<SocialSecurityChange> getChangesByDateRange(LocalDate startDate, LocalDate endDate) {
        LambdaQueryWrapper<SocialSecurityChange> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(SocialSecurityChange::getEffectiveDate, startDate, endDate);
        queryWrapper.orderByDesc(SocialSecurityChange::getEffectiveDate);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<SocialSecurityChange> getChangesByType(Integer changeType) {
        LambdaQueryWrapper<SocialSecurityChange> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SocialSecurityChange::getChangeType, changeType);
        queryWrapper.orderByDesc(SocialSecurityChange::getEffectiveDate);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public SocialSecurityChange getChangeById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean approveChange(Long id, Integer status) {
        SocialSecurityChange change = baseMapper.selectById(id);
        if (change != null) {
            change.setStatus(status); // 2-已批准
            change.setUpdateTime(LocalDateTime.now());
            return updateById(change);
        }
        return false;
    }

    @Override
    public boolean rejectChange(Long id) {
        SocialSecurityChange change = baseMapper.selectById(id);
        if (change != null) {
            change.setStatus(3); // 3-已拒绝
            change.setUpdateTime(LocalDateTime.now());
            return updateById(change);
        }
        return false;
    }
}