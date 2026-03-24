package com.hr.controller;

import com.hr.entity.HolidayType;
import com.hr.service.HolidayTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holiday/type")
public class HolidayTypeController {

    @Autowired
    private HolidayTypeService holidayTypeService;

    @GetMapping("/tenant/{tenantId}")
    public List<HolidayType> getTypesByTenantId(@PathVariable Long tenantId) {
        return holidayTypeService.getTypesByTenantId(tenantId);
    }

    @GetMapping("/{id}")
    public HolidayType getTypeById(@PathVariable Long id) {
        return holidayTypeService.getTypeById(id);
    }

    @PostMapping
    public boolean addType(@RequestBody HolidayType type) {
        return holidayTypeService.addType(type);
    }

    @PutMapping
    public boolean updateType(@RequestBody HolidayType type) {
        return holidayTypeService.updateType(type);
    }

    @DeleteMapping("/{id}")
    public boolean deleteType(@PathVariable Long id) {
        return holidayTypeService.deleteType(id);
    }
}