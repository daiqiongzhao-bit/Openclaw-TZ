package com.hr.controller;

import com.hr.entity.Department;
import com.hr.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/tree/{tenantId}")
    public List<Department> getDeptTree(@PathVariable Long tenantId) {
        return departmentService.getDeptTreeByTenantId(tenantId);
    }

    @GetMapping("/{id}")
    public Department getDeptById(@PathVariable Long id) {
        return departmentService.getDeptById(id);
    }

    @PostMapping
    public boolean addDept(@RequestBody Department dept) {
        return departmentService.addDept(dept);
    }

    @PutMapping
    public boolean updateDept(@RequestBody Department dept) {
        return departmentService.updateDept(dept);
    }

    @DeleteMapping("/{id}")
    public boolean deleteDept(@PathVariable Long id) {
        return departmentService.deleteDept(id);
    }
}