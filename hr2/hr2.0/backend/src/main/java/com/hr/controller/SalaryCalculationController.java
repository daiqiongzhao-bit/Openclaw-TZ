package com.hr.controller;

import com.hr.entity.SalaryCalculation;
import com.hr.service.SalaryCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/salary/calculation")
public class SalaryCalculationController {

    @Autowired
    private SalaryCalculationService salaryCalculationService;

    @PostMapping("/calculate")
    public boolean calculateSalary(@RequestParam Long userId, @RequestParam String salaryMonth) {
        LocalDate localSalaryMonth = LocalDate.parse(salaryMonth);
        return salaryCalculationService.calculateSalary(userId, localSalaryMonth);
    }

    @PostMapping("/batchCalculate")
    public boolean batchCalculateSalary(@RequestParam List<Long> userIds, @RequestParam String salaryMonth) {
        LocalDate localSalaryMonth = LocalDate.parse(salaryMonth);
        return salaryCalculationService.batchCalculateSalary(userIds, localSalaryMonth);
    }

    @GetMapping("/user/{userId}")
    public List<SalaryCalculation> getCalculationsByUserId(@PathVariable Long userId) {
        return salaryCalculationService.getCalculationsByUserId(userId);
    }

    @GetMapping("/dept/{deptId}")
    public List<SalaryCalculation> getCalculationsByDeptId(@PathVariable Long deptId) {
        return salaryCalculationService.getCalculationsByDeptId(deptId);
    }

    @GetMapping("/month/{salaryMonth}")
    public List<SalaryCalculation> getCalculationsByMonth(@PathVariable String salaryMonth) {
        LocalDate localSalaryMonth = LocalDate.parse(salaryMonth);
        return salaryCalculationService.getCalculationsByMonth(localSalaryMonth);
    }

    @GetMapping("/{id}")
    public SalaryCalculation getCalculationById(@PathVariable Long id) {
        return salaryCalculationService.getCalculationById(id);
    }
}