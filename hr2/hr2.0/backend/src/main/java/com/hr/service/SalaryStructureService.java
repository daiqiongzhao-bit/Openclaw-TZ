package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.SalaryStructure;

import java.util.List;

public interface SalaryStructureService extends IService<SalaryStructure> {
    List<SalaryStructure> getStructuresByTenantId(Long tenantId);
    SalaryStructure getStructureById(Long id);
    boolean addStructure(SalaryStructure structure);
    boolean updateStructure(SalaryStructure structure);
    boolean deleteStructure(Long id);
}