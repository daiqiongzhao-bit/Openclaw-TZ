package com.hr.controller;

import com.hr.entity.Employee;
import com.hr.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @PutMapping
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/department/{departmentId}")
    public List<Employee> getEmployeesByDepartmentId(@PathVariable Long departmentId) {
        return employeeService.getEmployeesByDepartmentId(departmentId);
    }

    @GetMapping("/position/{positionId}")
    public List<Employee> getEmployeesByPositionId(@PathVariable Long positionId) {
        return employeeService.getEmployeesByPositionId(positionId);
    }

    @GetMapping("/grade/{gradeId}")
    public List<Employee> getEmployeesByGradeId(@PathVariable Long gradeId) {
        return employeeService.getEmployeesByGradeId(gradeId);
    }

    @GetMapping("/status/{status}")
    public List<Employee> getEmployeesByStatus(@PathVariable String status) {
        return employeeService.getEmployeesByStatus(status);
    }

    @GetMapping("/search")
    public List<Employee> searchEmployees(@RequestParam String keyword) {
        return employeeService.searchEmployees(keyword);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<Employee> getEmployeesByTenantId(@PathVariable Long tenantId) {
        return employeeService.getEmployeesByTenantId(tenantId);
    }
}