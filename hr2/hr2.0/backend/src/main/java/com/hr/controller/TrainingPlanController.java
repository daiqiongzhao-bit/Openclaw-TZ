package com.hr.controller;

import com.hr.entity.TrainingPlan;
import com.hr.service.TrainingPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/training/plan")
public class TrainingPlanController {
    @Autowired
    private TrainingPlanService trainingPlanService;

    @PostMapping
    public TrainingPlan addTrainingPlan(@RequestBody TrainingPlan plan) {
        return trainingPlanService.addTrainingPlan(plan);
    }

    @PutMapping
    public TrainingPlan updateTrainingPlan(@RequestBody TrainingPlan plan) {
        return trainingPlanService.updateTrainingPlan(plan);
    }

    @DeleteMapping("/{id}")
    public void deleteTrainingPlan(@PathVariable Long id) {
        trainingPlanService.deleteTrainingPlan(id);
    }

    @GetMapping("/{id}")
    public TrainingPlan getTrainingPlanById(@PathVariable Long id) {
        return trainingPlanService.getTrainingPlanById(id);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<TrainingPlan> getTrainingPlansByTenantId(@PathVariable Long tenantId) {
        return trainingPlanService.getTrainingPlansByTenantId(tenantId);
    }

    @GetMapping("/date-range")
    public List<TrainingPlan> getTrainingPlansByDateRange(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return trainingPlanService.getTrainingPlansByDateRange(startDate, endDate);
    }

    @GetMapping("/status/{status}")
    public List<TrainingPlan> getTrainingPlansByStatus(@PathVariable String status) {
        return trainingPlanService.getTrainingPlansByStatus(status);
    }

    @GetMapping("/search")
    public List<TrainingPlan> searchTrainingPlans(@RequestParam String keyword) {
        return trainingPlanService.searchTrainingPlans(keyword);
    }

    @PutMapping("/submit/{id}")
    public TrainingPlan submitTrainingPlan(@PathVariable Long id) {
        return trainingPlanService.submitTrainingPlan(id);
    }

    @PutMapping("/approve/{id}")
    public TrainingPlan approveTrainingPlan(@PathVariable Long id) {
        return trainingPlanService.approveTrainingPlan(id);
    }

    @PutMapping("/reject/{id}")
    public TrainingPlan rejectTrainingPlan(@PathVariable Long id) {
        return trainingPlanService.rejectTrainingPlan(id);
    }
}