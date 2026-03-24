package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.SocialSecurityChange;

import java.time.LocalDate;
import java.util.List;

public interface SocialSecurityChangeService extends IService<SocialSecurityChange> {
    boolean applyChange(SocialSecurityChange change);
    List<SocialSecurityChange> getChangesByUserId(Long userId);
    List<SocialSecurityChange> getChangesByDateRange(LocalDate startDate, LocalDate endDate);
    List<SocialSecurityChange> getChangesByType(Integer changeType);
    SocialSecurityChange getChangeById(Long id);
    boolean approveChange(Long id, Integer status);
    boolean rejectChange(Long id);
}