package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.ProcessTemplate;

import java.util.List;
import java.util.Map;

public interface ProcessTemplateService extends IService<ProcessTemplate> {
    List<ProcessTemplate> getTemplatesByTenantId(Long tenantId);
    ProcessTemplate getTemplateById(Long id);
    boolean addTemplate(ProcessTemplate template);
    boolean updateTemplate(ProcessTemplate template);
    boolean deleteTemplate(Long id);
    boolean deployTemplate(Long id);
    boolean undeployTemplate(Long id);
}