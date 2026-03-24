package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.Dashboard;

import java.util.List;

public interface DashboardService extends IService<Dashboard> {
    Dashboard addDashboard(Dashboard dashboard);
    Dashboard updateDashboard(Dashboard dashboard);
    void deleteDashboard(Long id);
    Dashboard getDashboardById(Long id);
    List<Dashboard> getDashboardsByTenantId(Long tenantId);
    List<Dashboard> getDashboardsByType(String type);
    List<Dashboard> getActiveDashboards(Long tenantId);
}