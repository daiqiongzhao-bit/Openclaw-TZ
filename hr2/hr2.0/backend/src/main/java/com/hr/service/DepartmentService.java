package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.Department;

import java.util.List;

public interface DepartmentService extends IService<Department> {
    List<Department> getDeptTreeByTenantId(Long tenantId);
    Department getDeptById(Long id);
    boolean addDept(Department dept);
    boolean updateDept(Department dept);
    boolean deleteDept(Long id);
}