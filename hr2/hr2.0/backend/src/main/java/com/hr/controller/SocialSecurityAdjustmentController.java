package com.hr.controller;

import com.hr.entity.SocialSecurityAdjustment;
import com.hr.service.SocialSecurityAdjustmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/social/adjustment")
public class SocialSecurityAdjustmentController {

    @Autowired
    private SocialSecurityAdjustmentService socialSecurityAdjustmentService;

    @PostMapping
    public boolean applyAdjustment(@RequestBody SocialSecurityAdjustment adjustment) {
        return socialSecurityAdjustmentService.applyAdjustment(adjustment);
    }

    @GetMapping("/user/{userId}")
    public List<SocialSecurityAdjustment> getAdjustmentsByUserId(@PathVariable Long userId) {
        return socialSecurityAdjustmentService.getAdjustmentsByUserId(userId);
    }

    @GetMapping("/range")
    public List<SocialSecurityAdjustment> getAdjustmentsByDateRange(@RequestParam String startDate, @RequestParam String endDate) {
        LocalDate localStartDate = LocalDate.parse(startDate);
        LocalDate localEndDate = LocalDate.parse(endDate);
        return socialSecurityAdjustmentService.getAdjustmentsByDateRange(localStartDate, localEndDate);
    }

    @GetMapping("/{id}")
    public SocialSecurityAdjustment getAdjustmentById(@PathVariable Long id) {
        return socialSecurityAdjustmentService.getAdjustmentById(id);
    }

    @PostMapping("/approve/{id}")
    public boolean approveAdjustment(@PathVariable Long id, @RequestParam Integer status) {
        return socialSecurityAdjustmentService.approveAdjustment(id, status);
    }

    @PostMapping("/reject/{id}")
    public boolean rejectAdjustment(@PathVariable Long id) {
        return socialSecurityAdjustmentService.rejectAdjustment(id);
    }
}