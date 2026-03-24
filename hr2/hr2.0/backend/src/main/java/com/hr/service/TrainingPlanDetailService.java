package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.TrainingPlanDetail;

import java.time.LocalDate;
import java.util.List;

public interface TrainingPlanDetailService extends IService<TrainingPlanDetail> {
    TrainingPlanDetail addTrainingPlanDetail(TrainingPlanDetail detail);
    TrainingPlanDetail updateTrainingPlanDetail(TrainingPlanDetail detail);
    void deleteTrainingPlanDetail(Long id);
    TrainingPlanDetail getTrainingPlanDetailById(Long id);
    List<TrainingPlanDetail> getTrainingPlanDetailsByPlanId(Long planId);
    List<TrainingPlanDetail> getTrainingPlanDetailsByCourseId(Long courseId);
    List<TrainingPlanDetail> getTrainingPlanDetailsByInstructorId(Long instructorId);
    List<TrainingPlanDetail> getTrainingPlanDetailsByDateRange(LocalDate startDate, LocalDate endDate);
    List<TrainingPlanDetail> getTrainingPlanDetailsByTenantId(Long tenantId);
}