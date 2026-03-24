package com.hr.controller;

import com.hr.entity.SalaryStructure;
import com.hr.service.SalaryStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salary/structure")
public class SalaryStructureController {

    @Autowired
    private SalaryStructureService salaryStructureService;

    @GetMapping("/tenant/{tenantId}")
    public List<SalaryStructure> getStructuresByTenantId(@PathVariable Long tenantId) {
        return salaryStructureService.getStructuresByTenantId(tenantId);
    }

    @GetMapping("/{id}")
    public SalaryStructure getStructureById(@PathVariable Long id) {
        return salaryStructureService.getStructureById(id);
    }

    @PostMapping
    public boolean addStructure(@RequestBody SalaryStructure structure) {
        return salaryStructureService.addStructure(structure);
    }

    @PutMapping
    public boolean updateStructure(@RequestBody SalaryStructure structure) {
        return salaryStructureService.updateStructure(structure);
    }

    @DeleteMapping("/{id}")
    public boolean deleteStructure(@PathVariable Long id) {
        return salaryStructureService.deleteStructure(id);
    }
}