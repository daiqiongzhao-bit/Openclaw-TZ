package com.hr.controller;

import com.hr.entity.PerformanceGoal;
import com.hr.service.PerformanceGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performance/goal")
public class PerformanceGoalController {
    @Autowired
    private PerformanceGoalService performanceGoalService;

    @PostMapping
    public PerformanceGoal addGoal(@RequestBody PerformanceGoal goal) {
        return performanceGoalService.addGoal(goal);
    }

    @PutMapping
    public PerformanceGoal updateGoal(@RequestBody PerformanceGoal goal) {
        return performanceGoalService.updateGoal(goal);
    }

    @DeleteMapping("/{id}")
    public void deleteGoal(@PathVariable Long id) {
        performanceGoalService.deleteGoal(id);
    }

    @GetMapping("/{id}")
    public PerformanceGoal getGoalById(@PathVariable Long id) {
        return performanceGoalService.getGoalById(id);
    }

    @GetMapping("/appraisal/{appraisalId}")
    public List<PerformanceGoal> getGoalsByAppraisalId(@PathVariable Long appraisalId) {
        return performanceGoalService.getGoalsByAppraisalId(appraisalId);
    }

    @GetMapping("/employee/{employeeId}")
    public List<PerformanceGoal> getGoalsByEmployeeId(@PathVariable Long employeeId) {
        return performanceGoalService.getGoalsByEmployeeId(employeeId);
    }

    @GetMapping("/status/{status}")
    public List<PerformanceGoal> getGoalsByStatus(@PathVariable String status) {
        return performanceGoalService.getGoalsByStatus(status);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<PerformanceGoal> getGoalsByTenantId(@PathVariable Long tenantId) {
        return performanceGoalService.getGoalsByTenantId(tenantId);
    }
}