package com.hr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.TrainingPlanDetailDao;
import com.hr.entity.TrainingPlanDetail;
import com.hr.service.TrainingPlanDetailService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TrainingPlanDetailServiceImpl extends ServiceImpl<TrainingPlanDetailDao, TrainingPlanDetail> implements TrainingPlanDetailService {
    @Override
    public TrainingPlanDetail addTrainingPlanDetail(TrainingPlanDetail detail) {
        detail.setStatus("scheduled");
        save(detail);
        return detail;
    }

    @Override
    public TrainingPlanDetail updateTrainingPlanDetail(TrainingPlanDetail detail) {
        updateById(detail);
        return detail;
    }

    @Override
    public void deleteTrainingPlanDetail(Long id) {
        removeById(id);
    }

    @Override
    public TrainingPlanDetail getTrainingPlanDetailById(Long id) {
        return getById(id);
    }

    @Override
    public List<TrainingPlanDetail> getTrainingPlanDetailsByPlanId(Long planId) {
        return lambdaQuery().eq(TrainingPlanDetail::getPlanId, planId).list();
    }

    @Override
    public List<TrainingPlanDetail> getTrainingPlanDetailsByCourseId(Long courseId) {
        return lambdaQuery().eq(TrainingPlanDetail::getCourseId, courseId).list();
    }

    @Override
    public List<TrainingPlanDetail> getTrainingPlanDetailsByInstructorId(Long instructorId) {
        return lambdaQuery().eq(TrainingPlanDetail::getInstructorId, instructorId).list();
    }

    @Override
    public List<TrainingPlanDetail> getTrainingPlanDetailsByDateRange(LocalDate startDate, LocalDate endDate) {
        return lambdaQuery()
                .ge(TrainingPlanDetail::getTrainingDate, startDate)
                .le(TrainingPlanDetail::getTrainingDate, endDate)
                .list();
    }

    @Override
    public List<TrainingPlanDetail> getTrainingPlanDetailsByTenantId(Long tenantId) {
        return lambdaQuery().eq(TrainingPlanDetail::getTenantId, tenantId).list();
    }
}