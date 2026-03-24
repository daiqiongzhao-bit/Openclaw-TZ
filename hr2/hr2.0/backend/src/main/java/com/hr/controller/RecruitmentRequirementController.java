package com.hr.controller;

import com.hr.entity.RecruitmentRequirement;
import com.hr.service.RecruitmentRequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/recruitment/requirement")
public class RecruitmentRequirementController {
    @Autowired
    private RecruitmentRequirementService recruitmentRequirementService;

    @PostMapping
    public boolean submitRequirement(@RequestBody RecruitmentRequirement requirement) {
        return recruitmentRequirementService.submitRequirement(requirement);
    }

    @PutMapping("/approve/{id}")
    public boolean approveRequirement(@PathVariable Long id, @RequestParam Integer status) {
        return recruitmentRequirementService.approveRequirement(id, status);
    }

    @PutMapping("/reject/{id}")
    public boolean rejectRequirement(@PathVariable Long id) {
        return recruitmentRequirementService.rejectRequirement(id);
    }

    @GetMapping("/{id}")
    public RecruitmentRequirement getRequirementById(@PathVariable Long id) {
        return recruitmentRequirementService.getRequirementById(id);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<RecruitmentRequirement> getRequirementsByTenantId(@PathVariable Long tenantId) {
        return recruitmentRequirementService.getRequirementsByTenantId(tenantId);
    }

    @GetMapping("/dept/{deptId}")
    public List<RecruitmentRequirement> getRequirementsByDeptId(@PathVariable Long deptId) {
        return recruitmentRequirementService.getRequirementsByDeptId(deptId);
    }

    @GetMapping("/status/{status}")
    public List<RecruitmentRequirement> getRequirementsByStatus(@PathVariable Integer status) {
        return recruitmentRequirementService.getRequirementsByStatus(status);
    }

    @GetMapping("/date-range")
    public List<RecruitmentRequirement> getRequirementsByDateRange(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return recruitmentRequirementService.getRequirementsByDateRange(startDate, endDate);
    }
}