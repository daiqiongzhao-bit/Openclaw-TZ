package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.PerformanceAppraisal;

import java.time.LocalDate;
import java.util.List;

public interface PerformanceAppraisalService extends IService<PerformanceAppraisal> {
    PerformanceAppraisal createAppraisal(PerformanceAppraisal appraisal);
    PerformanceAppraisal updateAppraisal(PerformanceAppraisal appraisal);
    void deleteAppraisal(Long id);
    PerformanceAppraisal getAppraisalById(Long id);
    List<PerformanceAppraisal> getAppraisalsByEmployeeId(Long employeeId);
    List<PerformanceAppraisal> getAppraisalsByDepartmentId(Long departmentId);
    List<PerformanceAppraisal> getAppraisalsByCycle(String cycle);
    List<PerformanceAppraisal> getAppraisalsByDateRange(LocalDate startDate, LocalDate endDate);
    List<PerformanceAppraisal> getAppraisalsByStatus(String status);
    List<PerformanceAppraisal> getAppraisalsByTenantId(Long tenantId);
    PerformanceAppraisal submitAppraisal(Long id);
    PerformanceAppraisal approveAppraisal(Long id);
    PerformanceAppraisal rejectAppraisal(Long id);
}