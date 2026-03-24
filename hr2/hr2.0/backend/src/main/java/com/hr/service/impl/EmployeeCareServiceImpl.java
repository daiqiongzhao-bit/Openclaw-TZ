package com.hr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.EmployeeCareDao;
import com.hr.entity.EmployeeCare;
import com.hr.service.EmployeeCareService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeCareServiceImpl extends ServiceImpl<EmployeeCareDao, EmployeeCare> implements EmployeeCareService {
    @Override
    public EmployeeCare addEmployeeCare(EmployeeCare employeeCare) {
        employeeCare.setStatus("completed");
        save(employeeCare);
        return employeeCare;
    }

    @Override
    public EmployeeCare updateEmployeeCare(EmployeeCare employeeCare) {
        updateById(employeeCare);
        return employeeCare;
    }

    @Override
    public void deleteEmployeeCare(Long id) {
        removeById(id);
    }

    @Override
    public EmployeeCare getEmployeeCareById(Long id) {
        return getById(id);
    }

    @Override
    public List<EmployeeCare> getEmployeeCaresByEmployeeId(Long employeeId) {
        return lambdaQuery().eq(EmployeeCare::getEmployeeId, employeeId).list();
    }

    @Override
    public List<EmployeeCare> getEmployeeCaresByType(String type) {
        return lambdaQuery().eq(EmployeeCare::getType, type).list();
    }

    @Override
    public List<EmployeeCare> getEmployeeCaresByDateRange(LocalDate startDate, LocalDate endDate) {
        return lambdaQuery()
                .ge(EmployeeCare::getCareDate, startDate)
                .le(EmployeeCare::getCareDate, endDate)
                .list();
    }

    @Override
    public List<EmployeeCare> getEmployeeCaresByTenantId(Long tenantId) {
        return lambdaQuery().eq(EmployeeCare::getTenantId, tenantId).list();
    }
}