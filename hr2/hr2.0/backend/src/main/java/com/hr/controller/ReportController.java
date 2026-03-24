package com.hr.controller;

import com.hr.entity.Report;
import com.hr.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bi/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @PostMapping
    public Report addReport(@RequestBody Report report) {
        return reportService.addReport(report);
    }

    @PutMapping
    public Report updateReport(@RequestBody Report report) {
        return reportService.updateReport(report);
    }

    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
    }

    @GetMapping("/{id}")
    public Report getReportById(@PathVariable Long id) {
        return reportService.getReportById(id);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<Report> getReportsByTenantId(@PathVariable Long tenantId) {
        return reportService.getReportsByTenantId(tenantId);
    }

    @GetMapping("/type/{type}")
    public List<Report> getReportsByType(@PathVariable String type) {
        return reportService.getReportsByType(type);
    }

    @GetMapping("/active/{tenantId}")
    public List<Report> getActiveReports(@PathVariable Long tenantId) {
        return reportService.getActiveReports(tenantId);
    }

    @GetMapping("/export/{id}")
    public byte[] exportReport(@PathVariable Long id, @RequestParam String format) {
        return reportService.exportReport(id, format);
    }
}