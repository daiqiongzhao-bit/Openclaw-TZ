package com.hr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.ReportDao;
import com.hr.entity.Report;
import com.hr.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl extends ServiceImpl<ReportDao, Report> implements ReportService {
    @Override
    public Report addReport(Report report) {
        report.setStatus("active");
        save(report);
        return report;
    }

    @Override
    public Report updateReport(Report report) {
        updateById(report);
        return report;
    }

    @Override
    public void deleteReport(Long id) {
        removeById(id);
    }

    @Override
    public Report getReportById(Long id) {
        return getById(id);
    }

    @Override
    public List<Report> getReportsByTenantId(Long tenantId) {
        return lambdaQuery().eq(Report::getTenantId, tenantId).list();
    }

    @Override
    public List<Report> getReportsByType(String type) {
        return lambdaQuery().eq(Report::getType, type).list();
    }

    @Override
    public List<Report> getActiveReports(Long tenantId) {
        return lambdaQuery().eq(Report::getTenantId, tenantId).eq(Report::getStatus, "active").list();
    }

    @Override
    public byte[] exportReport(Long id, String format) {
        // 实现报表导出逻辑
        return new byte[0];
    }
}