package com.hr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.RecruitmentRequirementDao;
import com.hr.entity.RecruitmentRequirement;
import com.hr.service.RecruitmentRequirementService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecruitmentRequirementServiceImpl extends ServiceImpl<RecruitmentRequirementDao, RecruitmentRequirement> implements RecruitmentRequirementService {
    @Override
    public boolean submitRequirement(RecruitmentRequirement requirement) {
        requirement.setStatus(1); // 待审批
        return save(requirement);
    }

    @Override
    public List<RecruitmentRequirement> getRequirementsByTenantId(Long tenantId) {
        return lambdaQuery().eq(RecruitmentRequirement::getTenantId, tenantId).list();
    }

    @Override
    public List<RecruitmentRequirement> getRequirementsByDeptId(Long deptId) {
        return lambdaQuery().eq(RecruitmentRequirement::getDeptId, deptId).list();
    }

    @Override
    public List<RecruitmentRequirement> getRequirementsByStatus(Integer status) {
        return lambdaQuery().eq(RecruitmentRequirement::getStatus, status).list();
    }

    @Override
    public List<RecruitmentRequirement> getRequirementsByDateRange(LocalDate startDate, LocalDate endDate) {
        return lambdaQuery()
                .ge(RecruitmentRequirement::getCreateTime, startDate)
                .le(RecruitmentRequirement::getCreateTime, endDate)
                .list();
    }

    @Override
    public RecruitmentRequirement getRequirementById(Long id) {
        return getById(id);
    }

    @Override
    public boolean approveRequirement(Long id, Integer status) {
        RecruitmentRequirement requirement = getById(id);
        if (requirement != null) {
            requirement.setStatus(status);
            return updateById(requirement);
        }
        return false;
    }

    @Override
    public boolean rejectRequirement(Long id) {
        RecruitmentRequirement requirement = getById(id);
        if (requirement != null) {
            requirement.setStatus(3); // 已拒绝
            return updateById(requirement);
        }
        return false;
    }
}