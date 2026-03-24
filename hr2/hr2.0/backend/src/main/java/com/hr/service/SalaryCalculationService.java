package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.SalaryCalculation;

import java.time.LocalDate;
import java.util.List;

public interface SalaryCalculationService extends IService<SalaryCalculation> {
    boolean calculateSalary(Long userId, LocalDate salaryMonth);
    boolean batchCalculateSalary(List<Long> userIds, LocalDate salaryMonth);
    List<SalaryCalculation> getCalculationsByUserId(Long userId);
    List<SalaryCalculation> getCalculationsByDeptId(Long deptId);
    List<SalaryCalculation> getCalculationsByMonth(LocalDate salaryMonth);
    SalaryCalculation getCalculationById(Long id);
}