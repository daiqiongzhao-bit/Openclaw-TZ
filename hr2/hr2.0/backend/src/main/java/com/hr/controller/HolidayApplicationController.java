package com.hr.controller;

import com.hr.entity.HolidayApplication;
import com.hr.service.HolidayApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holiday/application")
public class HolidayApplicationController {

    @Autowired
    private HolidayApplicationService holidayApplicationService;

    @PostMapping
    public boolean submitApplication(@RequestBody HolidayApplication application) {
        return holidayApplicationService.submitApplication(application);
    }

    @GetMapping("/user/{userId}")
    public List<HolidayApplication> getApplicationsByUserId(@PathVariable Long userId) {
        return holidayApplicationService.getApplicationsByUserId(userId);
    }

    @GetMapping("/dept/{deptId}")
    public List<HolidayApplication> getApplicationsByDeptId(@PathVariable Long deptId) {
        return holidayApplicationService.getApplicationsByDeptId(deptId);
    }

    @GetMapping("/status/{status}")
    public List<HolidayApplication> getApplicationsByStatus(@PathVariable Integer status) {
        return holidayApplicationService.getApplicationsByStatus(status);
    }

    @GetMapping("/{id}")
    public HolidayApplication getApplicationById(@PathVariable Long id) {
        return holidayApplicationService.getApplicationById(id);
    }

    @PostMapping("/approve/{id}")
    public boolean approveApplication(@PathVariable Long id, @RequestParam Integer status, @RequestParam String comment) {
        return holidayApplicationService.approveApplication(id, status, comment);
    }

    @PostMapping("/reject/{id}")
    public boolean rejectApplication(@PathVariable Long id, @RequestParam String comment) {
        return holidayApplicationService.rejectApplication(id, comment);
    }
}