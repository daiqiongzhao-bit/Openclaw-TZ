package com.hr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.InterviewRecordDao;
import com.hr.entity.InterviewRecord;
import com.hr.service.InterviewRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InterviewRecordServiceImpl extends ServiceImpl<InterviewRecordDao, InterviewRecord> implements InterviewRecordService {
    @Override
    public InterviewRecord addInterviewRecord(InterviewRecord record) {
        save(record);
        return record;
    }

    @Override
    public InterviewRecord updateInterviewResult(Long id, Integer result) {
        InterviewRecord record = getById(id);
        if (record != null) {
            record.setResult(result);
            updateById(record);
        }
        return record;
    }

    @Override
    public List<InterviewRecord> getRecordsByResumeId(Long resumeId) {
        return lambdaQuery().eq(InterviewRecord::getResumeId, resumeId).list();
    }

    @Override
    public List<InterviewRecord> getRecordsByDateRange(LocalDate startDate, LocalDate endDate) {
        return lambdaQuery()
                .ge(InterviewRecord::getInterviewDate, startDate)
                .le(InterviewRecord::getInterviewDate, endDate)
                .list();
    }

    @Override
    public List<InterviewRecord> getRecordsByInterviewer(String interviewer) {
        return lambdaQuery().eq(InterviewRecord::getInterviewer, interviewer).list();
    }

    @Override
    public List<InterviewRecord> getRecordsByTenantId(Long tenantId) {
        return lambdaQuery().eq(InterviewRecord::getTenantId, tenantId).list();
    }
}