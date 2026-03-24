package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.EmployeeCare;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeCareService extends IService<EmployeeCare> {
    EmployeeCare addEmployeeCare(EmployeeCare employeeCare);
    EmployeeCare updateEmployeeCare(EmployeeCare employeeCare);
    void deleteEmployeeCare(Long id);
    EmployeeCare getEmployeeCareById(Long id);
    List<EmployeeCare> getEmployeeCaresByEmployeeId(Long employeeId);
    List<EmployeeCare> getEmployeeCaresByType(String type);
    List<EmployeeCare> getEmployeeCaresByDateRange(LocalDate startDate, LocalDate endDate);
    List<EmployeeCare> getEmployeeCaresByTenantId(Long tenantId);
}