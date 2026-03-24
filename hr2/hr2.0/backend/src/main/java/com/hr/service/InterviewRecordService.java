package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.InterviewRecord;

import java.time.LocalDate;
import java.util.List;

public interface InterviewRecordService extends IService<InterviewRecord> {
    boolean addInterviewRecord(InterviewRecord record);
    List<InterviewRecord> getRecordsByResumeId(Long resumeId);
    List<InterviewRecord> getRecordsByDateRange(LocalDate startDate, LocalDate endDate);
    List<InterviewRecord> getRecordsByInterviewer(String interviewer);
    InterviewRecord getRecordById(Long id);
    boolean updateInterviewResult(Long id, Integer result);
}