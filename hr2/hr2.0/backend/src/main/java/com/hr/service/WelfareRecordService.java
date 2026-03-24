package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.WelfareRecord;

import java.time.LocalDate;
import java.util.List;

public interface WelfareRecordService extends IService<WelfareRecord> {
    boolean issueWelfare(WelfareRecord record);
    boolean batchIssueWelfare(List<WelfareRecord> records);
    List<WelfareRecord> getRecordsByUserId(Long userId);
    List<WelfareRecord> getRecordsByDeptId(Long deptId);
    List<WelfareRecord> getRecordsByType(Long welfareTypeId);
    List<WelfareRecord> getRecordsByDateRange(LocalDate startDate, LocalDate endDate);
    WelfareRecord getRecordById(Long id);
}