package com.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.AttendanceRecordDao;
import com.hr.entity.AttendanceRecord;
import com.hr.service.AttendanceRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceRecordServiceImpl extends ServiceImpl<AttendanceRecordDao, AttendanceRecord> implements AttendanceRecordService {

    @Override
    public boolean checkIn(Long userId, String address, String device) {
        LocalDate today = LocalDate.now();
        AttendanceRecord record = getRecordByDate(userId, today);
        
        if (record == null) {
            record = new AttendanceRecord();
            record.setTenantId(1L); // 假设当前租户ID为1
            record.setUserId(userId);
            record.setUserName("用户" + userId); // 实际应该从用户服务中获取
            record.setDeptId(1L); // 实际应该从用户服务中获取
            record.setDeptName("技术部"); // 实际应该从用户服务中获取
            record.setAttendanceDate(today);
            record.setCheckInTime(LocalDateTime.now());
            record.setCheckInAddress(address);
            record.setCheckInDevice(device);
            record.setStatus(1); // 1-正常
            record.setCreateTime(LocalDateTime.now());
            record.setUpdateTime(LocalDateTime.now());
            return save(record);
        } else {
            record.setCheckInTime(LocalDateTime.now());
            record.setCheckInAddress(address);
            record.setCheckInDevice(device);
            record.setUpdateTime(LocalDateTime.now());
            return updateById(record);
        }
    }

    @Override
    public boolean checkOut(Long userId, String address, String device) {
        LocalDate today = LocalDate.now();
        AttendanceRecord record = getRecordByDate(userId, today);
        
        if (record == null) {
            record = new AttendanceRecord();
            record.setTenantId(1L); // 假设当前租户ID为1
            record.setUserId(userId);
            record.setUserName("用户" + userId); // 实际应该从用户服务中获取
            record.setDeptId(1L); // 实际应该从用户服务中获取
            record.setDeptName("技术部"); // 实际应该从用户服务中获取
            record.setAttendanceDate(today);
            record.setCheckOutTime(LocalDateTime.now());
            record.setCheckOutAddress(address);
            record.setCheckOutDevice(device);
            record.setStatus(1); // 1-正常
            record.setCreateTime(LocalDateTime.now());
            record.setUpdateTime(LocalDateTime.now());
            return save(record);
        } else {
            record.setCheckOutTime(LocalDateTime.now());
            record.setCheckOutAddress(address);
            record.setCheckOutDevice(device);
            record.setUpdateTime(LocalDateTime.now());
            return updateById(record);
        }
    }

    @Override
    public AttendanceRecord getRecordByDate(Long userId, LocalDate date) {
        LambdaQueryWrapper<AttendanceRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AttendanceRecord::getUserId, userId);
        queryWrapper.eq(AttendanceRecord::getAttendanceDate, date);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<AttendanceRecord> getRecordsByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        LambdaQueryWrapper<AttendanceRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AttendanceRecord::getUserId, userId);
        queryWrapper.between(AttendanceRecord::getAttendanceDate, startDate, endDate);
        queryWrapper.orderByAsc(AttendanceRecord::getAttendanceDate);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<AttendanceRecord> getRecordsByDeptIdAndDateRange(Long deptId, LocalDate startDate, LocalDate endDate) {
        LambdaQueryWrapper<AttendanceRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AttendanceRecord::getDeptId, deptId);
        queryWrapper.between(AttendanceRecord::getAttendanceDate, startDate, endDate);
        queryWrapper.orderByAsc(AttendanceRecord::getAttendanceDate);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Map<String, Object> getAttendanceStats(Long userId, LocalDate startDate, LocalDate endDate) {
        List<AttendanceRecord> records = getRecordsByUserIdAndDateRange(userId, startDate, endDate);
        
        int totalDays = 0;
        int normalDays = 0;
        int lateDays = 0;
        int earlyLeaveDays = 0;
        int absentDays = 0;
        
        // 计算工作日天数
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            // 简单判断，实际应该根据工作日历判断
            if (current.getDayOfWeek().getValue() < 6) {
                totalDays++;
            }
            current = current.plusDays(1);
        }
        
        // 统计考勤状态
        for (AttendanceRecord record : records) {
            if (record.getCheckInTime() != null && record.getCheckOutTime() != null) {
                // 简单判断，实际应该根据考勤规则判断
                LocalTime checkInTime = record.getCheckInTime().toLocalTime();
                LocalTime checkOutTime = record.getCheckOutTime().toLocalTime();
                
                if (checkInTime.isAfter(LocalTime.of(9, 0))) {
                    lateDays++;
                } else if (checkOutTime.isBefore(LocalTime.of(18, 0))) {
                    earlyLeaveDays++;
                } else {
                    normalDays++;
                }
            } else {
                absentDays++;
            }
        }
        
        // 计算缺勤天数
        absentDays = totalDays - records.size();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalDays", totalDays);
        stats.put("normalDays", normalDays);
        stats.put("lateDays", lateDays);
        stats.put("earlyLeaveDays", earlyLeaveDays);
        stats.put("absentDays", absentDays);
        stats.put("attendanceRate", totalDays > 0 ? (double)(totalDays - absentDays) / totalDays * 100 : 0);
        
        return stats;
    }
}