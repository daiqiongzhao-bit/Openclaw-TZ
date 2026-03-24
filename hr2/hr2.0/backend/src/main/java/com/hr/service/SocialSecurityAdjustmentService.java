package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.SocialSecurityAdjustment;

import java.time.LocalDate;
import java.util.List;

public interface SocialSecurityAdjustmentService extends IService<SocialSecurityAdjustment> {
    boolean applyAdjustment(SocialSecurityAdjustment adjustment);
    List<SocialSecurityAdjustment> getAdjustmentsByUserId(Long userId);
    List<SocialSecurityAdjustment> getAdjustmentsByDateRange(LocalDate startDate, LocalDate endDate);
    SocialSecurityAdjustment getAdjustmentById(Long id);
    boolean approveAdjustment(Long id, Integer status);
    boolean rejectAdjustment(Long id);
}