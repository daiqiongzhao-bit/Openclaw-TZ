package com.hr.controller;

import com.hr.entity.WelfareRecord;
import com.hr.service.WelfareRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/welfare/record")
public class WelfareRecordController {

    @Autowired
    private WelfareRecordService welfareRecordService;

    @PostMapping
    public boolean issueWelfare(@RequestBody WelfareRecord record) {
        return welfareRecordService.issueWelfare(record);
    }

    @PostMapping("/batch")
    public boolean batchIssueWelfare(@RequestBody List<WelfareRecord> records) {
        return welfareRecordService.batchIssueWelfare(records);
    }

    @GetMapping("/user/{userId}")
    public List<WelfareRecord> getRecordsByUserId(@PathVariable Long userId) {
        return welfareRecordService.getRecordsByUserId(userId);
    }

    @GetMapping("/dept/{deptId}")
    public List<WelfareRecord> getRecordsByDeptId(@PathVariable Long deptId) {
        return welfareRecordService.getRecordsByDeptId(deptId);
    }

    @GetMapping("/type/{welfareTypeId}")
    public List<WelfareRecord> getRecordsByType(@PathVariable Long welfareTypeId) {
        return welfareRecordService.getRecordsByType(welfareTypeId);
    }

    @GetMapping("/range")
    public List<WelfareRecord> getRecordsByDateRange(@RequestParam String startDate, @RequestParam String endDate) {
        LocalDate localStartDate = LocalDate.parse(startDate);
        LocalDate localEndDate = LocalDate.parse(endDate);
        return welfareRecordService.getRecordsByDateRange(localStartDate, localEndDate);
    }

    @GetMapping("/{id}")
    public WelfareRecord getRecordById(@PathVariable Long id) {
        return welfareRecordService.getRecordById(id);
    }
}