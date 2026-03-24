package com.hr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.DashboardDao;
import com.hr.entity.Dashboard;
import com.hr.service.DashboardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl extends ServiceImpl<DashboardDao, Dashboard> implements DashboardService {
    @Override
    public Dashboard addDashboard(Dashboard dashboard) {
        dashboard.setStatus("active");
        save(dashboard);
        return dashboard;
    }

    @Override
    public Dashboard updateDashboard(Dashboard dashboard) {
        updateById(dashboard);
        return dashboard;
    }

    @Override
    public void deleteDashboard(Long id) {
        removeById(id);
    }

    @Override
    public Dashboard getDashboardById(Long id) {
        return getById(id);
    }

    @Override
    public List<Dashboard> getDashboardsByTenantId(Long tenantId) {
        return lambdaQuery().eq(Dashboard::getTenantId, tenantId).list();
    }

    @Override
    public List<Dashboard> getDashboardsByType(String type) {
        return lambdaQuery().eq(Dashboard::getType, type).list();
    }

    @Override
    public List<Dashboard> getActiveDashboards(Long tenantId) {
        return lambdaQuery().eq(Dashboard::getTenantId, tenantId).eq(Dashboard::getStatus, "active").list();
    }
}