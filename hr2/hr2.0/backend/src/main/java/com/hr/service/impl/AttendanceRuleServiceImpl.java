package com.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.AttendanceRuleDao;
import com.hr.entity.AttendanceRule;
import com.hr.service.AttendanceRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceRuleServiceImpl extends ServiceImpl<AttendanceRuleDao, AttendanceRule> implements AttendanceRuleService {

    @Override
    public List<AttendanceRule> getRulesByTenantId(Long tenantId) {
        LambdaQueryWrapper<AttendanceRule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AttendanceRule::getTenantId, tenantId);
        queryWrapper.eq(AttendanceRule::getStatus, 1);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public AttendanceRule getRuleById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean addRule(AttendanceRule rule) {
        return save(rule);
    }

    @Override
    public boolean updateRule(AttendanceRule rule) {
        return updateById(rule);
    }

    @Override
    public boolean deleteRule(Long id) {
        return removeById(id);
    }
}