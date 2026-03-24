package com.hr.controller;

import com.hr.entity.WelfareType;
import com.hr.service.WelfareTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/welfare/type")
public class WelfareTypeController {

    @Autowired
    private WelfareTypeService welfareTypeService;

    @GetMapping("/tenant/{tenantId}")
    public List<WelfareType> getTypesByTenantId(@PathVariable Long tenantId) {
        return welfareTypeService.getTypesByTenantId(tenantId);
    }

    @GetMapping("/{id}")
    public WelfareType getTypeById(@PathVariable Long id) {
        return welfareTypeService.getTypeById(id);
    }

    @PostMapping
    public boolean addType(@RequestBody WelfareType type) {
        return welfareTypeService.addType(type);
    }

    @PutMapping
    public boolean updateType(@RequestBody WelfareType type) {
        return welfareTypeService.updateType(type);
    }

    @DeleteMapping("/{id}")
    public boolean deleteType(@PathVariable Long id) {
        return welfareTypeService.deleteType(id);
    }
}