package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.Report;

import java.util.List;

public interface ReportService extends IService<Report> {
    Report addReport(Report report);
    Report updateReport(Report report);
    void deleteReport(Long id);
    Report getReportById(Long id);
    List<Report> getReportsByTenantId(Long tenantId);
    List<Report> getReportsByType(String type);
    List<Report> getActiveReports(Long tenantId);
    byte[] exportReport(Long id, String format);
}