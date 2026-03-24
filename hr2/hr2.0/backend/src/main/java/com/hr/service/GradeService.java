package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.Grade;

import java.util.List;

public interface GradeService extends IService<Grade> {
    List<Grade> getGradesByTenantId(Long tenantId);
    Grade getGradeById(Long id);
    boolean addGrade(Grade grade);
    boolean updateGrade(Grade grade);
    boolean deleteGrade(Long id);
}