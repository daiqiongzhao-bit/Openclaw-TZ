package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.Employee;

import java.util.List;

public interface EmployeeService extends IService<Employee> {
    Employee addEmployee(Employee employee);
    Employee updateEmployee(Employee employee);
    void deleteEmployee(Long id);
    Employee getEmployeeById(Long id);
    List<Employee> getEmployeesByDepartmentId(Long departmentId);
    List<Employee> getEmployeesByPositionId(Long positionId);
    List<Employee> getEmployeesByGradeId(Long gradeId);
    List<Employee> getEmployeesByStatus(String status);
    List<Employee> searchEmployees(String keyword);
    List<Employee> getEmployeesByTenantId(Long tenantId);
}