package com.hr.controller;

import com.hr.entity.ProcessTemplate;
import com.hr.service.ProcessTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/process/template")
public class ProcessTemplateController {

    @Autowired
    private ProcessTemplateService processTemplateService;

    @GetMapping("/tenant/{tenantId}")
    public List<ProcessTemplate> getTemplatesByTenantId(@PathVariable Long tenantId) {
        return processTemplateService.getTemplatesByTenantId(tenantId);
    }

    @GetMapping("/{id}")
    public ProcessTemplate getTemplateById(@PathVariable Long id) {
        return processTemplateService.getTemplateById(id);
    }

    @PostMapping
    public boolean addTemplate(@RequestBody ProcessTemplate template) {
        return processTemplateService.addTemplate(template);
    }

    @PutMapping
    public boolean updateTemplate(@RequestBody ProcessTemplate template) {
        return processTemplateService.updateTemplate(template);
    }

    @DeleteMapping("/{id}")
    public boolean deleteTemplate(@PathVariable Long id) {
        return processTemplateService.deleteTemplate(id);
    }

    @PostMapping("/deploy/{id}")
    public boolean deployTemplate(@PathVariable Long id) {
        return processTemplateService.deployTemplate(id);
    }

    @PostMapping("/undeploy/{id}")
    public boolean undeployTemplate(@PathVariable Long id) {
        return processTemplateService.undeployTemplate(id);
    }
}