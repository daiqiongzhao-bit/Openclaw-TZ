package com.hr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.TrainingPlanDao;
import com.hr.entity.TrainingPlan;
import com.hr.service.TrainingPlanService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TrainingPlanServiceImpl extends ServiceImpl<TrainingPlanDao, TrainingPlan> implements TrainingPlanService {
    @Override
    public TrainingPlan addTrainingPlan(TrainingPlan plan) {
        plan.setStatus("draft");
        save(plan);
        return plan;
    }

    @Override
    public TrainingPlan updateTrainingPlan(TrainingPlan plan) {
        updateById(plan);
        return plan;
    }

    @Override
    public void deleteTrainingPlan(Long id) {
        removeById(id);
    }

    @Override
    public TrainingPlan getTrainingPlanById(Long id) {
        return getById(id);
    }

    @Override
    public List<TrainingPlan> getTrainingPlansByTenantId(Long tenantId) {
        return lambdaQuery().eq(TrainingPlan::getTenantId, tenantId).list();
    }

    @Override
    public List<TrainingPlan> getTrainingPlansByDateRange(LocalDate startDate, LocalDate endDate) {
        return lambdaQuery()
                .ge(TrainingPlan::getStartDate, startDate)
                .le(TrainingPlan::getEndDate, endDate)
                .list();
    }

    @Override
    public List<TrainingPlan> getTrainingPlansByStatus(String status) {
        return lambdaQuery().eq(TrainingPlan::getStatus, status).list();
    }

    @Override
    public List<TrainingPlan> searchTrainingPlans(String keyword) {
        return lambdaQuery()
                .like(TrainingPlan::getPlanName, keyword)
                .or()
                .like(TrainingPlan::getPlanCode, keyword)
                .or()
                .like(TrainingPlan::getDescription, keyword)
                .list();
    }

    @Override
    public TrainingPlan submitTrainingPlan(Long id) {
        TrainingPlan plan = getById(id);
        if (plan != null) {
            plan.setStatus("pending");
            updateById(plan);
        }
        return plan;
    }

    @Override
    public TrainingPlan approveTrainingPlan(Long id) {
        TrainingPlan plan = getById(id);
        if (plan != null) {
            plan.setStatus("approved");
            updateById(plan);
        }
        return plan;
    }

    @Override
    public TrainingPlan rejectTrainingPlan(Long id) {
        TrainingPlan plan = getById(id);
        if (plan != null) {
            plan.setStatus("rejected");
            updateById(plan);
        }
        return plan;
    }
}