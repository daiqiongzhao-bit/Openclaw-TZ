package com.hr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.PerformanceGoalDao;
import com.hr.entity.PerformanceGoal;
import com.hr.service.PerformanceGoalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceGoalServiceImpl extends ServiceImpl<PerformanceGoalDao, PerformanceGoal> implements PerformanceGoalService {
    @Override
    public PerformanceGoal addGoal(PerformanceGoal goal) {
        goal.setStatus("draft");
        save(goal);
        return goal;
    }

    @Override
    public PerformanceGoal updateGoal(PerformanceGoal goal) {
        updateById(goal);
        return goal;
    }

    @Override
    public void deleteGoal(Long id) {
        removeById(id);
    }

    @Override
    public PerformanceGoal getGoalById(Long id) {
        return getById(id);
    }

    @Override
    public List<PerformanceGoal> getGoalsByAppraisalId(Long appraisalId) {
        return lambdaQuery().eq(PerformanceGoal::getAppraisalId, appraisalId).list();
    }

    @Override
    public List<PerformanceGoal> getGoalsByEmployeeId(Long employeeId) {
        return lambdaQuery().eq(PerformanceGoal::getEmployeeId, employeeId).list();
    }

    @Override
    public List<PerformanceGoal> getGoalsByStatus(String status) {
        return lambdaQuery().eq(PerformanceGoal::getStatus, status).list();
    }

    @Override
    public List<PerformanceGoal> getGoalsByTenantId(Long tenantId) {
        return lambdaQuery().eq(PerformanceGoal::getTenantId, tenantId).list();
    }
}