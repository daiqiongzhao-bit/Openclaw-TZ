package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.PerformanceTemplate;

import java.util.List;

public interface PerformanceTemplateService extends IService<PerformanceTemplate> {
    PerformanceTemplate addTemplate(PerformanceTemplate template);
    PerformanceTemplate updateTemplate(PerformanceTemplate template);
    void deleteTemplate(Long id);
    PerformanceTemplate getTemplateById(Long id);
    List<PerformanceTemplate> getTemplatesByTenantId(Long tenantId);
    List<PerformanceTemplate> getTemplatesByType(String type);
    List<PerformanceTemplate> getActiveTemplates(Long tenantId);
}