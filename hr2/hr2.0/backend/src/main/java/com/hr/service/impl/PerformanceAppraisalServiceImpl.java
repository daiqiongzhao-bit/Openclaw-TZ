package com.hr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.PerformanceAppraisalDao;
import com.hr.entity.PerformanceAppraisal;
import com.hr.service.PerformanceAppraisalService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PerformanceAppraisalServiceImpl extends ServiceImpl<PerformanceAppraisalDao, PerformanceAppraisal> implements PerformanceAppraisalService {
    @Override
    public PerformanceAppraisal createAppraisal(PerformanceAppraisal appraisal) {
        appraisal.setStatus("draft");
        save(appraisal);
        return appraisal;
    }

    @Override
    public PerformanceAppraisal updateAppraisal(PerformanceAppraisal appraisal) {
        updateById(appraisal);
        return appraisal;
    }

    @Override
    public void deleteAppraisal(Long id) {
        removeById(id);
    }

    @Override
    public PerformanceAppraisal getAppraisalById(Long id) {
        return getById(id);
    }

    @Override
    public List<PerformanceAppraisal> getAppraisalsByEmployeeId(Long employeeId) {
        return lambdaQuery().eq(PerformanceAppraisal::getEmployeeId, employeeId).list();
    }

    @Override
    public List<PerformanceAppraisal> getAppraisalsByDepartmentId(Long departmentId) {
        return lambdaQuery().eq(PerformanceAppraisal::getDepartmentId, departmentId).list();
    }

    @Override
    public List<PerformanceAppraisal> getAppraisalsByCycle(String cycle) {
        return lambdaQuery().eq(PerformanceAppraisal::getCycle, cycle).list();
    }

    @Override
    public List<PerformanceAppraisal> getAppraisalsByDateRange(LocalDate startDate, LocalDate endDate) {
        return lambdaQuery()
                .ge(PerformanceAppraisal::getStartDate, startDate)
                .le(PerformanceAppraisal::getEndDate, endDate)
                .list();
    }

    @Override
    public List<PerformanceAppraisal> getAppraisalsByStatus(String status) {
        return lambdaQuery().eq(PerformanceAppraisal::getStatus, status).list();
    }

    @Override
    public List<PerformanceAppraisal> getAppraisalsByTenantId(Long tenantId) {
        return lambdaQuery().eq(PerformanceAppraisal::getTenantId, tenantId).list();
    }

    @Override
    public PerformanceAppraisal submitAppraisal(Long id) {
        PerformanceAppraisal appraisal = getById(id);
        if (appraisal != null) {
            appraisal.setStatus("pending");
            updateById(appraisal);
        }
        return appraisal;
    }

    @Override
    public PerformanceAppraisal approveAppraisal(Long id) {
        PerformanceAppraisal appraisal = getById(id);
        if (appraisal != null) {
            appraisal.setStatus("approved");
            updateById(appraisal);
        }
        return appraisal;
    }

    @Override
    public PerformanceAppraisal rejectAppraisal(Long id) {
        PerformanceAppraisal appraisal = getById(id);
        if (appraisal != null) {
            appraisal.setStatus("rejected");
            updateById(appraisal);
        }
        return appraisal;
    }
}