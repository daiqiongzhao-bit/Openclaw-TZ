package com.hr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.ResumeDao;
import com.hr.entity.Resume;
import com.hr.service.ResumeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeServiceImpl extends ServiceImpl<ResumeDao, Resume> implements ResumeService {
    @Override
    public Resume addResume(Resume resume) {
        save(resume);
        return resume;
    }

    @Override
    public Resume updateResume(Resume resume) {
        updateById(resume);
        return resume;
    }

    @Override
    public void deleteResume(Long id) {
        removeById(id);
    }

    @Override
    public Resume getResumeById(Long id) {
        return getById(id);
    }

    @Override
    public List<Resume> getResumesByRequirementId(Long requirementId) {
        return lambdaQuery().eq(Resume::getRequirementId, requirementId).list();
    }

    @Override
    public List<Resume> getResumesByStatus(Integer status) {
        return lambdaQuery().eq(Resume::getStatus, status).list();
    }

    @Override
    public List<Resume> searchResumes(String keyword) {
        return lambdaQuery()
                .like(Resume::getName, keyword)
                .or()
                .like(Resume::getPhone, keyword)
                .or()
                .like(Resume::getEmail, keyword)
                .or()
                .like(Resume::getEducation, keyword)
                .or()
                .like(Resume::getWorkExperience, keyword)
                .list();
    }

    @Override
    public List<Resume> getResumesByTenantId(Long tenantId) {
        return lambdaQuery().eq(Resume::getTenantId, tenantId).list();
    }
}