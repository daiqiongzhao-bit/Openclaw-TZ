package com.hr.controller;

import com.hr.entity.PerformanceAppraisal;
import com.hr.service.PerformanceAppraisalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/performance/appraisal")
public class PerformanceAppraisalController {
    @Autowired
    private PerformanceAppraisalService performanceAppraisalService;

    @PostMapping
    public PerformanceAppraisal createAppraisal(@RequestBody PerformanceAppraisal appraisal) {
        return performanceAppraisalService.createAppraisal(appraisal);
    }

    @PutMapping
    public PerformanceAppraisal updateAppraisal(@RequestBody PerformanceAppraisal appraisal) {
        return performanceAppraisalService.updateAppraisal(appraisal);
    }

    @DeleteMapping("/{id}")
    public void deleteAppraisal(@PathVariable Long id) {
        performanceAppraisalService.deleteAppraisal(id);
    }

    @GetMapping("/{id}")
    public PerformanceAppraisal getAppraisalById(@PathVariable Long id) {
        return performanceAppraisalService.getAppraisalById(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<PerformanceAppraisal> getAppraisalsByEmployeeId(@PathVariable Long employeeId) {
        return performanceAppraisalService.getAppraisalsByEmployeeId(employeeId);
    }

    @GetMapping("/department/{departmentId}")
    public List<PerformanceAppraisal> getAppraisalsByDepartmentId(@PathVariable Long departmentId) {
        return performanceAppraisalService.getAppraisalsByDepartmentId(departmentId);
    }

    @GetMapping("/cycle/{cycle}")
    public List<PerformanceAppraisal> getAppraisalsByCycle(@PathVariable String cycle) {
        return performanceAppraisalService.getAppraisalsByCycle(cycle);
    }

    @GetMapping("/date-range")
    public List<PerformanceAppraisal> getAppraisalsByDateRange(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return performanceAppraisalService.getAppraisalsByDateRange(startDate, endDate);
    }

    @GetMapping("/status/{status}")
    public List<PerformanceAppraisal> getAppraisalsByStatus(@PathVariable String status) {
        return performanceAppraisalService.getAppraisalsByStatus(status);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<PerformanceAppraisal> getAppraisalsByTenantId(@PathVariable Long tenantId) {
        return performanceAppraisalService.getAppraisalsByTenantId(tenantId);
    }

    @PutMapping("/submit/{id}")
    public PerformanceAppraisal submitAppraisal(@PathVariable Long id) {
        return performanceAppraisalService.submitAppraisal(id);
    }

    @PutMapping("/approve/{id}")
    public PerformanceAppraisal approveAppraisal(@PathVariable Long id) {
        return performanceAppraisalService.approveAppraisal(id);
    }

    @PutMapping("/reject/{id}")
    public PerformanceAppraisal rejectAppraisal(@PathVariable Long id) {
        return performanceAppraisalService.rejectAppraisal(id);
    }
}