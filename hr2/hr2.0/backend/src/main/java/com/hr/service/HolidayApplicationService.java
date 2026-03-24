package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.HolidayApplication;

import java.time.LocalDate;
import java.util.List;

public interface HolidayApplicationService extends IService<HolidayApplication> {
    boolean submitApplication(HolidayApplication application);
    List<HolidayApplication> getApplicationsByUserId(Long userId);
    List<HolidayApplication> getApplicationsByDeptId(Long deptId);
    List<HolidayApplication> getApplicationsByStatus(Integer status);
    HolidayApplication getApplicationById(Long id);
    boolean approveApplication(Long id, Integer status, String comment);
    boolean rejectApplication(Long id, String comment);
}