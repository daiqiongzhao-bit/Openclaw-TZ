package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.Instructor;

import java.util.List;

public interface InstructorService extends IService<Instructor> {
    Instructor addInstructor(Instructor instructor);
    Instructor updateInstructor(Instructor instructor);
    void deleteInstructor(Long id);
    Instructor getInstructorById(Long id);
    List<Instructor> getInstructorsByTenantId(Long tenantId);
    List<Instructor> getInstructorsByExpertise(String expertise);
    List<Instructor> getActiveInstructors(Long tenantId);
    List<Instructor> searchInstructors(String keyword);
}