package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.PersonnelNotice;

import java.time.LocalDate;
import java.util.List;

public interface PersonnelNoticeService extends IService<PersonnelNotice> {
    PersonnelNotice addPersonnelNotice(PersonnelNotice notice);
    PersonnelNotice updatePersonnelNotice(PersonnelNotice notice);
    void deletePersonnelNotice(Long id);
    PersonnelNotice getPersonnelNoticeById(Long id);
    List<PersonnelNotice> getPersonnelNoticesByEmployeeId(Long employeeId);
    List<PersonnelNotice> getPersonnelNoticesByType(String type);
    List<PersonnelNotice> getPersonnelNoticesByDateRange(LocalDate startDate, LocalDate endDate);
    List<PersonnelNotice> getPersonnelNoticesByTenantId(Long tenantId);
}