package com.hr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.PerformanceTemplateDao;
import com.hr.entity.PerformanceTemplate;
import com.hr.service.PerformanceTemplateService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceTemplateServiceImpl extends ServiceImpl<PerformanceTemplateDao, PerformanceTemplate> implements PerformanceTemplateService {
    @Override
    public PerformanceTemplate addTemplate(PerformanceTemplate template) {
        save(template);
        return template;
    }

    @Override
    public PerformanceTemplate updateTemplate(PerformanceTemplate template) {
        updateById(template);
        return template;
    }

    @Override
    public void deleteTemplate(Long id) {
        removeById(id);
    }

    @Override
    public PerformanceTemplate getTemplateById(Long id) {
        return getById(id);
    }

    @Override
    public List<PerformanceTemplate> getTemplatesByTenantId(Long tenantId) {
        return lambdaQuery().eq(PerformanceTemplate::getTenantId, tenantId).list();
    }

    @Override
    public List<PerformanceTemplate> getTemplatesByType(String type) {
        return lambdaQuery().eq(PerformanceTemplate::getType, type).list();
    }

    @Override
    public List<PerformanceTemplate> getActiveTemplates(Long tenantId) {
        return lambdaQuery().eq(PerformanceTemplate::getTenantId, tenantId).eq(PerformanceTemplate::getStatus, "active").list();
    }
}