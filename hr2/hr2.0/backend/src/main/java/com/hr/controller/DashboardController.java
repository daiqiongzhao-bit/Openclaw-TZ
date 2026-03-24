package com.hr.controller;

import com.hr.entity.Dashboard;
import com.hr.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bi/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @PostMapping
    public Dashboard addDashboard(@RequestBody Dashboard dashboard) {
        return dashboardService.addDashboard(dashboard);
    }

    @PutMapping
    public Dashboard updateDashboard(@RequestBody Dashboard dashboard) {
        return dashboardService.updateDashboard(dashboard);
    }

    @DeleteMapping("/{id}")
    public void deleteDashboard(@PathVariable Long id) {
        dashboardService.deleteDashboard(id);
    }

    @GetMapping("/{id}")
    public Dashboard getDashboardById(@PathVariable Long id) {
        return dashboardService.getDashboardById(id);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<Dashboard> getDashboardsByTenantId(@PathVariable Long tenantId) {
        return dashboardService.getDashboardsByTenantId(tenantId);
    }

    @GetMapping("/type/{type}")
    public List<Dashboard> getDashboardsByType(@PathVariable String type) {
        return dashboardService.getDashboardsByType(type);
    }

    @GetMapping("/active/{tenantId}")
    public List<Dashboard> getActiveDashboards(@PathVariable Long tenantId) {
        return dashboardService.getActiveDashboards(tenantId);
    }
}