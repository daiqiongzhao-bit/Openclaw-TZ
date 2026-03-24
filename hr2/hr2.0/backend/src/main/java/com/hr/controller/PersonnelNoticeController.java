package com.hr.controller;

import com.hr.entity.PersonnelNotice;
import com.hr.service.PersonnelNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/employee-relation/notice")
public class PersonnelNoticeController {
    @Autowired
    private PersonnelNoticeService personnelNoticeService;

    @PostMapping
    public PersonnelNotice addPersonnelNotice(@RequestBody PersonnelNotice notice) {
        return personnelNoticeService.addPersonnelNotice(notice);
    }

    @PutMapping
    public PersonnelNotice updatePersonnelNotice(@RequestBody PersonnelNotice notice) {
        return personnelNoticeService.updatePersonnelNotice(notice);
    }

    @DeleteMapping("/{id}")
    public void deletePersonnelNotice(@PathVariable Long id) {
        personnelNoticeService.deletePersonnelNotice(id);
    }

    @GetMapping("/{id}")
    public PersonnelNotice getPersonnelNoticeById(@PathVariable Long id) {
        return personnelNoticeService.getPersonnelNoticeById(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<PersonnelNotice> getPersonnelNoticesByEmployeeId(@PathVariable Long employeeId) {
        return personnelNoticeService.getPersonnelNoticesByEmployeeId(employeeId);
    }

    @GetMapping("/type/{type}")
    public List<PersonnelNotice> getPersonnelNoticesByType(@PathVariable String type) {
        return personnelNoticeService.getPersonnelNoticesByType(type);
    }

    @GetMapping("/date-range")
    public List<PersonnelNotice> getPersonnelNoticesByDateRange(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return personnelNoticeService.getPersonnelNoticesByDateRange(startDate, endDate);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<PersonnelNotice> getPersonnelNoticesByTenantId(@PathVariable Long tenantId) {
        return personnelNoticeService.getPersonnelNoticesByTenantId(tenantId);
    }
}