package com.hr.controller;

import com.hr.entity.EmployeeCare;
import com.hr.service.EmployeeCareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/employee-relation/care")
public class EmployeeCareController {
    @Autowired
    private EmployeeCareService employeeCareService;

    @PostMapping
    public EmployeeCare addEmployeeCare(@RequestBody EmployeeCare employeeCare) {
        return employeeCareService.addEmployeeCare(employeeCare);
    }

    @PutMapping
    public EmployeeCare updateEmployeeCare(@RequestBody EmployeeCare employeeCare) {
        return employeeCareService.updateEmployeeCare(employeeCare);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployeeCare(@PathVariable Long id) {
        employeeCareService.deleteEmployeeCare(id);
    }

    @GetMapping("/{id}")
    public EmployeeCare getEmployeeCareById(@PathVariable Long id) {
        return employeeCareService.getEmployeeCareById(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<EmployeeCare> getEmployeeCaresByEmployeeId(@PathVariable Long employeeId) {
        return employeeCareService.getEmployeeCaresByEmployeeId(employeeId);
    }

    @GetMapping("/type/{type}")
    public List<EmployeeCare> getEmployeeCaresByType(@PathVariable String type) {
        return employeeCareService.getEmployeeCaresByType(type);
    }

    @GetMapping("/date-range")
    public List<EmployeeCare> getEmployeeCaresByDateRange(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return employeeCareService.getEmployeeCaresByDateRange(startDate, endDate);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<EmployeeCare> getEmployeeCaresByTenantId(@PathVariable Long tenantId) {
        return employeeCareService.getEmployeeCaresByTenantId(tenantId);
    }
}