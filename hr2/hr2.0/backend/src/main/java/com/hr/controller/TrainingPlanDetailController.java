package com.hr.controller;

import com.hr.entity.TrainingPlanDetail;
import com.hr.service.TrainingPlanDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/training/plan/detail")
public class TrainingPlanDetailController {
    @Autowired
    private TrainingPlanDetailService trainingPlanDetailService;

    @PostMapping
    public TrainingPlanDetail addTrainingPlanDetail(@RequestBody TrainingPlanDetail detail) {
        return trainingPlanDetailService.addTrainingPlanDetail(detail);
    }

    @PutMapping
    public TrainingPlanDetail updateTrainingPlanDetail(@RequestBody TrainingPlanDetail detail) {
        return trainingPlanDetailService.updateTrainingPlanDetail(detail);
    }

    @DeleteMapping("/{id}")
    public void deleteTrainingPlanDetail(@PathVariable Long id) {
        trainingPlanDetailService.deleteTrainingPlanDetail(id);
    }

    @GetMapping("/{id}")
    public TrainingPlanDetail getTrainingPlanDetailById(@PathVariable Long id) {
        return trainingPlanDetailService.getTrainingPlanDetailById(id);
    }

    @GetMapping("/plan/{planId}")
    public List<TrainingPlanDetail> getTrainingPlanDetailsByPlanId(@PathVariable Long planId) {
        return trainingPlanDetailService.getTrainingPlanDetailsByPlanId(planId);
    }

    @GetMapping("/course/{courseId}")
    public List<TrainingPlanDetail> getTrainingPlanDetailsByCourseId(@PathVariable Long courseId) {
        return trainingPlanDetailService.getTrainingPlanDetailsByCourseId(courseId);
    }

    @GetMapping("/instructor/{instructorId}")
    public List<TrainingPlanDetail> getTrainingPlanDetailsByInstructorId(@PathVariable Long instructorId) {
        return trainingPlanDetailService.getTrainingPlanDetailsByInstructorId(instructorId);
    }

    @GetMapping("/date-range")
    public List<TrainingPlanDetail> getTrainingPlanDetailsByDateRange(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return trainingPlanDetailService.getTrainingPlanDetailsByDateRange(startDate, endDate);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<TrainingPlanDetail> getTrainingPlanDetailsByTenantId(@PathVariable Long tenantId) {
        return trainingPlanDetailService.getTrainingPlanDetailsByTenantId(tenantId);
    }
}