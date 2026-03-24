package com.hr.controller;

import com.hr.entity.PerformanceTemplate;
import com.hr.service.PerformanceTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performance/template")
public class PerformanceTemplateController {
    @Autowired
    private PerformanceTemplateService performanceTemplateService;

    @PostMapping
    public PerformanceTemplate addTemplate(@RequestBody PerformanceTemplate template) {
        return performanceTemplateService.addTemplate(template);
    }

    @PutMapping
    public PerformanceTemplate updateTemplate(@RequestBody PerformanceTemplate template) {
        return performanceTemplateService.updateTemplate(template);
    }

    @DeleteMapping("/{id}")
    public void deleteTemplate(@PathVariable Long id) {
        performanceTemplateService.deleteTemplate(id);
    }

    @GetMapping("/{id}")
    public PerformanceTemplate getTemplateById(@PathVariable Long id) {
        return performanceTemplateService.getTemplateById(id);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<PerformanceTemplate> getTemplatesByTenantId(@PathVariable Long tenantId) {
        return performanceTemplateService.getTemplatesByTenantId(tenantId);
    }

    @GetMapping("/type/{type}")
    public List<PerformanceTemplate> getTemplatesByType(@PathVariable String type) {
        return performanceTemplateService.getTemplatesByType(type);
    }

    @GetMapping("/active/{tenantId}")
    public List<PerformanceTemplate> getActiveTemplates(@PathVariable Long tenantId) {
        return performanceTemplateService.getActiveTemplates(tenantId);
    }
}