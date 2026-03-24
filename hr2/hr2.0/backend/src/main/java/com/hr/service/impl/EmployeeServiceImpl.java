package com.hr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.EmployeeDao;
import com.hr.entity.Employee;
import com.hr.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, Employee> implements EmployeeService {
    @Override
    public Employee addEmployee(Employee employee) {
        save(employee);
        return employee;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        updateById(employee);
        return employee;
    }

    @Override
    public void deleteEmployee(Long id) {
        removeById(id);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return getById(id);
    }

    @Override
    public List<Employee> getEmployeesByDepartmentId(Long departmentId) {
        return lambdaQuery().eq(Employee::getDepartmentId, departmentId).list();
    }

    @Override
    public List<Employee> getEmployeesByPositionId(Long positionId) {
        return lambdaQuery().eq(Employee::getPositionId, positionId).list();
    }

    @Override
    public List<Employee> getEmployeesByGradeId(Long gradeId) {
        return lambdaQuery().eq(Employee::getGradeId, gradeId).list();
    }

    @Override
    public List<Employee> getEmployeesByStatus(String status) {
        return lambdaQuery().eq(Employee::getStatus, status).list();
    }

    @Override
    public List<Employee> searchEmployees(String keyword) {
        return lambdaQuery()
                .like(Employee::getName, keyword)
                .or()
                .like(Employee::getEmployeeId, keyword)
                .or()
                .like(Employee::getPhone, keyword)
                .or()
                .like(Employee::getEmail, keyword)
                .list();
    }

    @Override
    public List<Employee> getEmployeesByTenantId(Long tenantId) {
        return lambdaQuery().eq(Employee::getTenantId, tenantId).list();
    }
}