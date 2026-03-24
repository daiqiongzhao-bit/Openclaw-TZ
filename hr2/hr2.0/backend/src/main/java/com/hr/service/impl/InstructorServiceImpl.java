package com.hr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.InstructorDao;
import com.hr.entity.Instructor;
import com.hr.service.InstructorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorServiceImpl extends ServiceImpl<InstructorDao, Instructor> implements InstructorService {
    @Override
    public Instructor addInstructor(Instructor instructor) {
        instructor.setStatus("active");
        save(instructor);
        return instructor;
    }

    @Override
    public Instructor updateInstructor(Instructor instructor) {
        updateById(instructor);
        return instructor;
    }

    @Override
    public void deleteInstructor(Long id) {
        removeById(id);
    }

    @Override
    public Instructor getInstructorById(Long id) {
        return getById(id);
    }

    @Override
    public List<Instructor> getInstructorsByTenantId(Long tenantId) {
        return lambdaQuery().eq(Instructor::getTenantId, tenantId).list();
    }

    @Override
    public List<Instructor> getInstructorsByExpertise(String expertise) {
        return lambdaQuery().eq(Instructor::getExpertise, expertise).list();
    }

    @Override
    public List<Instructor> getActiveInstructors(Long tenantId) {
        return lambdaQuery().eq(Instructor::getTenantId, tenantId).eq(Instructor::getStatus, "active").list();
    }

    @Override
    public List<Instructor> searchInstructors(String keyword) {
        return lambdaQuery()
                .like(Instructor::getName, keyword)
                .or()
                .like(Instructor::getPhone, keyword)
                .or()
                .like(Instructor::getEmail, keyword)
                .or()
                .like(Instructor::getExpertise, keyword)
                .list();
    }
}