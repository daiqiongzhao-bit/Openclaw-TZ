package com.hr.controller;

import com.hr.entity.AttendanceRecord;
import com.hr.service.AttendanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance/record")
public class AttendanceRecordController {

    @Autowired
    private AttendanceRecordService attendanceRecordService;

    @PostMapping("/checkin")
    public boolean checkIn(@RequestParam Long userId, @RequestParam String address, @RequestParam String device) {
        return attendanceRecordService.checkIn(userId, address, device);
    }

    @PostMapping("/checkout")
    public boolean checkOut(@RequestParam Long userId, @RequestParam String address, @RequestParam String device) {
        return attendanceRecordService.checkOut(userId, address, device);
    }

    @GetMapping("/user/{userId}/date/{date}")
    public AttendanceRecord getRecordByDate(@PathVariable Long userId, @PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return attendanceRecordService.getRecordByDate(userId, localDate);
    }

    @GetMapping("/user/{userId}/range")
    public List<AttendanceRecord> getRecordsByUserIdAndDateRange(@PathVariable Long userId, @RequestParam String startDate, @RequestParam String endDate) {
        LocalDate localStartDate = LocalDate.parse(startDate);
        LocalDate localEndDate = LocalDate.parse(endDate);
        return attendanceRecordService.getRecordsByUserIdAndDateRange(userId, localStartDate, localEndDate);
    }

    @GetMapping("/dept/{deptId}/range")
    public List<AttendanceRecord> getRecordsByDeptIdAndDateRange(@PathVariable Long deptId, @RequestParam String startDate, @RequestParam String endDate) {
        LocalDate localStartDate = LocalDate.parse(startDate);
        LocalDate localEndDate = LocalDate.parse(endDate);
        return attendanceRecordService.getRecordsByDeptIdAndDateRange(deptId, localStartDate, localEndDate);
    }

    @GetMapping("/stats/{userId}/range")
    public Map<String, Object> getAttendanceStats(@PathVariable Long userId, @RequestParam String startDate, @RequestParam String endDate) {
        LocalDate localStartDate = LocalDate.parse(startDate);
        LocalDate localEndDate = LocalDate.parse(endDate);
        return attendanceRecordService.getAttendanceStats(userId, localStartDate, localEndDate);
    }
}