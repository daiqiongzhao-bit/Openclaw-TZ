package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.AttendanceRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AttendanceRecordService extends IService<AttendanceRecord> {
    boolean checkIn(Long userId, String address, String device);
    boolean checkOut(Long userId, String address, String device);
    AttendanceRecord getRecordByDate(Long userId, LocalDate date);
    List<AttendanceRecord> getRecordsByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);
    List<AttendanceRecord> getRecordsByDeptIdAndDateRange(Long deptId, LocalDate startDate, LocalDate endDate);
    Map<String, Object> getAttendanceStats(Long userId, LocalDate startDate, LocalDate endDate);
}