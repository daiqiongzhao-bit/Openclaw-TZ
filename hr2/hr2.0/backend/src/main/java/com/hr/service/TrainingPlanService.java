package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.TrainingPlan;

import java.time.LocalDate;
import java.util.List;

public interface TrainingPlanService extends IService<TrainingPlan> {
    TrainingPlan addTrainingPlan(TrainingPlan plan);
    TrainingPlan updateTrainingPlan(TrainingPlan plan);
    void deleteTrainingPlan(Long id);
    TrainingPlan getTrainingPlanById(Long id);
    List<TrainingPlan> getTrainingPlansByTenantId(Long tenantId);
    List<TrainingPlan> getTrainingPlansByDateRange(LocalDate startDate, LocalDate endDate);
    List<TrainingPlan> getTrainingPlansByStatus(String status);
    List<TrainingPlan> searchTrainingPlans(String keyword);
    TrainingPlan submitTrainingPlan(Long id);
    TrainingPlan approveTrainingPlan(Long id);
    TrainingPlan rejectTrainingPlan(Long id);
}