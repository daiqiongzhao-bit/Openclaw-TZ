package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.AttendanceRule;

import java.util.List;

public interface AttendanceRuleService extends IService<AttendanceRule> {
    List<AttendanceRule> getRulesByTenantId(Long tenantId);
    AttendanceRule getRuleById(Long id);
    boolean addRule(AttendanceRule rule);
    boolean updateRule(AttendanceRule rule);
    boolean deleteRule(Long id);
}