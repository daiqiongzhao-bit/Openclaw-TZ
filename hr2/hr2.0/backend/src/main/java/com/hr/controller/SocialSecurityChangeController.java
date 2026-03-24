package com.hr.controller;

import com.hr.entity.SocialSecurityChange;
import com.hr.service.SocialSecurityChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/social/change")
public class SocialSecurityChangeController {

    @Autowired
    private SocialSecurityChangeService socialSecurityChangeService;

    @PostMapping
    public boolean applyChange(@RequestBody SocialSecurityChange change) {
        return socialSecurityChangeService.applyChange(change);
    }

    @GetMapping("/user/{userId}")
    public List<SocialSecurityChange> getChangesByUserId(@PathVariable Long userId) {
        return socialSecurityChangeService.getChangesByUserId(userId);
    }

    @GetMapping("/range")
    public List<SocialSecurityChange> getChangesByDateRange(@RequestParam String startDate, @RequestParam String endDate) {
        LocalDate localStartDate = LocalDate.parse(startDate);
        LocalDate localEndDate = LocalDate.parse(endDate);
        return socialSecurityChangeService.getChangesByDateRange(localStartDate, localEndDate);
    }

    @GetMapping("/type/{changeType}")
    public List<SocialSecurityChange> getChangesByType(@PathVariable Integer changeType) {
        return socialSecurityChangeService.getChangesByType(changeType);
    }

    @GetMapping("/{id}")
    public SocialSecurityChange getChangeById(@PathVariable Long id) {
        return socialSecurityChangeService.getChangeById(id);
    }

    @PostMapping("/approve/{id}")
    public boolean approveChange(@PathVariable Long id, @RequestParam Integer status) {
        return socialSecurityChangeService.approveChange(id, status);
    }

    @PostMapping("/reject/{id}")
    public boolean rejectChange(@PathVariable Long id) {
        return socialSecurityChangeService.rejectChange(id);
    }
}