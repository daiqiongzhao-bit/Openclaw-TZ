package com.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.WelfareRecordDao;
import com.hr.entity.WelfareRecord;
import com.hr.service.WelfareRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WelfareRecordServiceImpl extends ServiceImpl<WelfareRecordDao, WelfareRecord> implements WelfareRecordService {

    @Override
    public boolean issueWelfare(WelfareRecord record) {
        record.setTenantId(1L); // 假设当前租户ID为1
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        return save(record);
    }

    @Override
    public boolean batchIssueWelfare(List<WelfareRecord> records) {
        for (WelfareRecord record : records) {
            record.setTenantId(1L); // 假设当前租户ID为1
            record.setCreateTime(LocalDateTime.now());
            record.setUpdateTime(LocalDateTime.now());
        }
        return saveBatch(records);
    }

    @Override
    public List<WelfareRecord> getRecordsByUserId(Long userId) {
        LambdaQueryWrapper<WelfareRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WelfareRecord::getUserId, userId);
        queryWrapper.orderByDesc(WelfareRecord::getIssueDate);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<WelfareRecord> getRecordsByDeptId(Long deptId) {
        LambdaQueryWrapper<WelfareRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WelfareRecord::getDeptId, deptId);
        queryWrapper.orderByDesc(WelfareRecord::getIssueDate);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<WelfareRecord> getRecordsByType(Long welfareTypeId) {
        LambdaQueryWrapper<WelfareRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WelfareRecord::getWelfareTypeId, welfareTypeId);
        queryWrapper.orderByDesc(WelfareRecord::getIssueDate);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<WelfareRecord> getRecordsByDateRange(LocalDate startDate, LocalDate endDate) {
        LambdaQueryWrapper<WelfareRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(WelfareRecord::getIssueDate, startDate, endDate);
        queryWrapper.orderByDesc(WelfareRecord::getIssueDate);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public WelfareRecord getRecordById(Long id) {
        return baseMapper.selectById(id);
    }
}