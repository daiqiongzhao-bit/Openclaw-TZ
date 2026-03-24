package com.hr.controller;

import com.hr.entity.AttendanceRule;
import com.hr.service.AttendanceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance/rule")
public class AttendanceRuleController {

    @Autowired
    private AttendanceRuleService attendanceRuleService;

    @GetMapping("/tenant/{tenantId}")
    public List<AttendanceRule> getRulesByTenantId(@PathVariable Long tenantId) {
        return attendanceRuleService.getRulesByTenantId(tenantId);
    }

    @GetMapping("/{id}")
    public AttendanceRule getRuleById(@PathVariable Long id) {
        return attendanceRuleService.getRuleById(id);
    }

    @PostMapping
    public boolean addRule(@RequestBody AttendanceRule rule) {
        return attendanceRuleService.addRule(rule);
    }

    @PutMapping
    public boolean updateRule(@RequestBody AttendanceRule rule) {
        return attendanceRuleService.updateRule(rule);
    }

    @DeleteMapping("/{id}")
    public boolean deleteRule(@PathVariable Long id) {
        return attendanceRuleService.deleteRule(id);
    }
}