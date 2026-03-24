package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.RecruitmentRequirement;

import java.time.LocalDate;
import java.util.List;

public interface RecruitmentRequirementService extends IService<RecruitmentRequirement> {
    boolean submitRequirement(RecruitmentRequirement requirement);
    List<RecruitmentRequirement> getRequirementsByTenantId(Long tenantId);
    List<RecruitmentRequirement> getRequirementsByDeptId(Long deptId);
    List<RecruitmentRequirement> getRequirementsByStatus(Integer status);
    List<RecruitmentRequirement> getRequirementsByDateRange(LocalDate startDate, LocalDate endDate);
    RecruitmentRequirement getRequirementById(Long id);
    boolean approveRequirement(Long id, Integer status);
    boolean rejectRequirement(Long id);
}