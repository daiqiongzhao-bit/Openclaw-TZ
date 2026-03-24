package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.PerformanceGoal;

import java.util.List;

public interface PerformanceGoalService extends IService<PerformanceGoal> {
    PerformanceGoal addGoal(PerformanceGoal goal);
    PerformanceGoal updateGoal(PerformanceGoal goal);
    void deleteGoal(Long id);
    PerformanceGoal getGoalById(Long id);
    List<PerformanceGoal> getGoalsByAppraisalId(Long appraisalId);
    List<PerformanceGoal> getGoalsByEmployeeId(Long employeeId);
    List<PerformanceGoal> getGoalsByStatus(String status);
    List<PerformanceGoal> getGoalsByTenantId(Long tenantId);
}