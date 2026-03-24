package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.RewardPunishment;

import java.time.LocalDate;
import java.util.List;

public interface RewardPunishmentService extends IService<RewardPunishment> {
    RewardPunishment addRewardPunishment(RewardPunishment rewardPunishment);
    RewardPunishment updateRewardPunishment(RewardPunishment rewardPunishment);
    void deleteRewardPunishment(Long id);
    RewardPunishment getRewardPunishmentById(Long id);
    List<RewardPunishment> getRewardPunishmentsByEmployeeId(Long employeeId);
    List<RewardPunishment> getRewardPunishmentsByType(String type);
    List<RewardPunishment> getRewardPunishmentsByDateRange(LocalDate startDate, LocalDate endDate);
    List<RewardPunishment> getRewardPunishmentsByTenantId(Long tenantId);
    RewardPunishment approveRewardPunishment(Long id);
    RewardPunishment rejectRewardPunishment(Long id);
}