package com.hr.controller;

import com.hr.entity.SalaryItem;
import com.hr.service.SalaryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salary/item")
public class SalaryItemController {

    @Autowired
    private SalaryItemService salaryItemService;

    @GetMapping("/structure/{structureId}")
    public List<SalaryItem> getItemsByStructureId(@PathVariable Long structureId) {
        return salaryItemService.getItemsByStructureId(structureId);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<SalaryItem> getItemsByTenantId(@PathVariable Long tenantId) {
        return salaryItemService.getItemsByTenantId(tenantId);
    }

    @GetMapping("/{id}")
    public SalaryItem getItemById(@PathVariable Long id) {
        return salaryItemService.getItemById(id);
    }

    @PostMapping
    public boolean addItem(@RequestBody SalaryItem item) {
        return salaryItemService.addItem(item);
    }

    @PutMapping
    public boolean updateItem(@RequestBody SalaryItem item) {
        return salaryItemService.updateItem(item);
    }

    @DeleteMapping("/{id}")
    public boolean deleteItem(@PathVariable Long id) {
        return salaryItemService.deleteItem(id);
    }
}