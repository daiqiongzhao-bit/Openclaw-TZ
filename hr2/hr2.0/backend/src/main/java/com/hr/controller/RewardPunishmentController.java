package com.hr.controller;

import com.hr.entity.RewardPunishment;
import com.hr.service.RewardPunishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/employee-relation/reward-punishment")
public class RewardPunishmentController {
    @Autowired
    private RewardPunishmentService rewardPunishmentService;

    @PostMapping
    public RewardPunishment addRewardPunishment(@RequestBody RewardPunishment rewardPunishment) {
        return rewardPunishmentService.addRewardPunishment(rewardPunishment);
    }

    @PutMapping
    public RewardPunishment updateRewardPunishment(@RequestBody RewardPunishment rewardPunishment) {
        return rewardPunishmentService.updateRewardPunishment(rewardPunishment);
    }

    @DeleteMapping("/{id}")
    public void deleteRewardPunishment(@PathVariable Long id) {
        rewardPunishmentService.deleteRewardPunishment(id);
    }

    @GetMapping("/{id}")
    public RewardPunishment getRewardPunishmentById(@PathVariable Long id) {
        return rewardPunishmentService.getRewardPunishmentById(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<RewardPunishment> getRewardPunishmentsByEmployeeId(@PathVariable Long employeeId) {
        return rewardPunishmentService.getRewardPunishmentsByEmployeeId(employeeId);
    }

    @GetMapping("/type/{type}")
    public List<RewardPunishment> getRewardPunishmentsByType(@PathVariable String type) {
        return rewardPunishmentService.getRewardPunishmentsByType(type);
    }

    @GetMapping("/date-range")
    public List<RewardPunishment> getRewardPunishmentsByDateRange(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return rewardPunishmentService.getRewardPunishmentsByDateRange(startDate, endDate);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<RewardPunishment> getRewardPunishmentsByTenantId(@PathVariable Long tenantId) {
        return rewardPunishmentService.getRewardPunishmentsByTenantId(tenantId);
    }

    @PutMapping("/approve/{id}")
    public RewardPunishment approveRewardPunishment(@PathVariable Long id) {
        return rewardPunishmentService.approveRewardPunishment(id);
    }

    @PutMapping("/reject/{id}")
    public RewardPunishment rejectRewardPunishment(@PathVariable Long id) {
        return rewardPunishmentService.rejectRewardPunishment(id);
    }
}