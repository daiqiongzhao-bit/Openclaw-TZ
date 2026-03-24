package com.hr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.RewardPunishmentDao;
import com.hr.entity.RewardPunishment;
import com.hr.service.RewardPunishmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RewardPunishmentServiceImpl extends ServiceImpl<RewardPunishmentDao, RewardPunishment> implements RewardPunishmentService {
    @Override
    public RewardPunishment addRewardPunishment(RewardPunishment rewardPunishment) {
        rewardPunishment.setStatus("pending");
        save(rewardPunishment);
        return rewardPunishment;
    }

    @Override
    public RewardPunishment updateRewardPunishment(RewardPunishment rewardPunishment) {
        updateById(rewardPunishment);
        return rewardPunishment;
    }

    @Override
    public void deleteRewardPunishment(Long id) {
        removeById(id);
    }

    @Override
    public RewardPunishment getRewardPunishmentById(Long id) {
        return getById(id);
    }

    @Override
    public List<RewardPunishment> getRewardPunishmentsByEmployeeId(Long employeeId) {
        return lambdaQuery().eq(RewardPunishment::getEmployeeId, employeeId).list();
    }

    @Override
    public List<RewardPunishment> getRewardPunishmentsByType(String type) {
        return lambdaQuery().eq(RewardPunishment::getType, type).list();
    }

    @Override
    public List<RewardPunishment> getRewardPunishmentsByDateRange(LocalDate startDate, LocalDate endDate) {
        return lambdaQuery()
                .ge(RewardPunishment::getEffectiveDate, startDate)
                .le(RewardPunishment::getEffectiveDate, endDate)
                .list();
    }

    @Override
    public List<RewardPunishment> getRewardPunishmentsByTenantId(Long tenantId) {
        return lambdaQuery().eq(RewardPunishment::getTenantId, tenantId).list();
    }

    @Override
    public RewardPunishment approveRewardPunishment(Long id) {
        RewardPunishment rewardPunishment = getById(id);
        if (rewardPunishment != null) {
            rewardPunishment.setStatus("approved");
            updateById(rewardPunishment);
        }
        return rewardPunishment;
    }

    @Override
    public RewardPunishment rejectRewardPunishment(Long id) {
        RewardPunishment rewardPunishment = getById(id);
        if (rewardPunishment != null) {
            rewardPunishment.setStatus("rejected");
            updateById(rewardPunishment);
        }
        return rewardPunishment;
    }
}