package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.Resume;

import java.util.List;

public interface ResumeService extends IService<Resume> {
    boolean uploadResume(Resume resume);
    List<Resume> getResumesByTenantId(Long tenantId);
    List<Resume> getResumesByPosition(String positionApplied);
    List<Resume> getResumesByStatus(Integer status);
    Resume getResumeById(Long id);
    boolean updateResumeStatus(Long id, Integer status);
}