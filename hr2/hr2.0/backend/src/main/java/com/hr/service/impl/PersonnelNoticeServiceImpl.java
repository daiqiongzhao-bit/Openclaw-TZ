package com.hr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.PersonnelNoticeDao;
import com.hr.entity.PersonnelNotice;
import com.hr.service.PersonnelNoticeService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PersonnelNoticeServiceImpl extends ServiceImpl<PersonnelNoticeDao, PersonnelNotice> implements PersonnelNoticeService {
    @Override
    public PersonnelNotice addPersonnelNotice(PersonnelNotice notice) {
        notice.setStatus("issued");
        save(notice);
        return notice;
    }

    @Override
    public PersonnelNotice updatePersonnelNotice(PersonnelNotice notice) {
        updateById(notice);
        return notice;
    }

    @Override
    public void deletePersonnelNotice(Long id) {
        removeById(id);
    }

    @Override
    public PersonnelNotice getPersonnelNoticeById(Long id) {
        return getById(id);
    }

    @Override
    public List<PersonnelNotice> getPersonnelNoticesByEmployeeId(Long employeeId) {
        return lambdaQuery().eq(PersonnelNotice::getEmployeeId, employeeId).list();
    }

    @Override
    public List<PersonnelNotice> getPersonnelNoticesByType(String type) {
        return lambdaQuery().eq(PersonnelNotice::getNoticeType, type).list();
    }

    @Override
    public List<PersonnelNotice> getPersonnelNoticesByDateRange(LocalDate startDate, LocalDate endDate) {
        return lambdaQuery()
                .ge(PersonnelNotice::getEffectiveDate, startDate)
                .le(PersonnelNotice::getEffectiveDate, endDate)
                .list();
    }

    @Override
    public List<PersonnelNotice> getPersonnelNoticesByTenantId(Long tenantId) {
        return lambdaQuery().eq(PersonnelNotice::getTenantId, tenantId).list();
    }
}