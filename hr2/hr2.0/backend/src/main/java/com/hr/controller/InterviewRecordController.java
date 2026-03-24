package com.hr.controller;

import com.hr.entity.InterviewRecord;
import com.hr.service.InterviewRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/recruitment/interview")
public class InterviewRecordController {
    @Autowired
    private InterviewRecordService interviewRecordService;

    @PostMapping
    public InterviewRecord addInterviewRecord(@RequestBody InterviewRecord record) {
        return interviewRecordService.addInterviewRecord(record);
    }

    @PutMapping("/result/{id}")
    public InterviewRecord updateInterviewResult(@PathVariable Long id, @RequestParam Integer result) {
        return interviewRecordService.updateInterviewResult(id, result);
    }

    @GetMapping("/resume/{resumeId}")
    public List<InterviewRecord> getRecordsByResumeId(@PathVariable Long resumeId) {
        return interviewRecordService.getRecordsByResumeId(resumeId);
    }

    @GetMapping("/date-range")
    public List<InterviewRecord> getRecordsByDateRange(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return interviewRecordService.getRecordsByDateRange(startDate, endDate);
    }

    @GetMapping("/interviewer/{interviewer}")
    public List<InterviewRecord> getRecordsByInterviewer(@PathVariable String interviewer) {
        return interviewRecordService.getRecordsByInterviewer(interviewer);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<InterviewRecord> getRecordsByTenantId(@PathVariable Long tenantId) {
        return interviewRecordService.getRecordsByTenantId(tenantId);
    }
}