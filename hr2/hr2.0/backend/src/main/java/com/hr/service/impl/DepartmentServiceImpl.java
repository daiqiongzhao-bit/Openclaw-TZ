package com.hr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.DepartmentDao;
import com.hr.entity.Department;
import com.hr.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentDao, Department> implements DepartmentService {

    @Override
    public List<Department> getDeptTreeByTenantId(Long tenantId) {
        List<Department> allDepts = baseMapper.selectList(null);
        return buildDeptTree(allDepts, 0L);
    }

    private List<Department> buildDeptTree(List<Department> allDepts, Long parentId) {
        return allDepts.stream()
                .filter(dept -> dept.getParentId().equals(parentId))
                .collect(Collectors.toList());
    }

    @Override
    public Department getDeptById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean addDept(Department dept) {
        return save(dept);
    }

    @Override
    public boolean updateDept(Department dept) {
        return updateById(dept);
    }

    @Override
    public boolean deleteDept(Long id) {
        return removeById(id);
    }
}