package com.hr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hr.dao.CertificateApplicationDao;
import com.hr.entity.CertificateApplication;
import com.hr.service.CertificateApplicationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CertificateApplicationServiceImpl extends ServiceImpl<CertificateApplicationDao, CertificateApplication> implements CertificateApplicationService {
    @Override
    public CertificateApplication submitCertificateApplication(CertificateApplication application) {
        application.setStatus("pending");
        save(application);
        return application;
    }

    @Override
    public CertificateApplication updateCertificateApplication(CertificateApplication application) {
        updateById(application);
        return application;
    }

    @Override
    public void deleteCertificateApplication(Long id) {
        removeById(id);
    }

    @Override
    public CertificateApplication getCertificateApplicationById(Long id) {
        return getById(id);
    }

    @Override
    public List<CertificateApplication> getCertificateApplicationsByEmployeeId(Long employeeId) {
        return lambdaQuery().eq(CertificateApplication::getEmployeeId, employeeId).list();
    }

    @Override
    public List<CertificateApplication> getCertificateApplicationsByType(String type) {
        return lambdaQuery().eq(CertificateApplication::getCertificateType, type).list();
    }

    @Override
    public List<CertificateApplication> getCertificateApplicationsByDateRange(LocalDate startDate, LocalDate endDate) {
        return lambdaQuery()
                .ge(CertificateApplication::getApplicationDate, startDate)
                .le(CertificateApplication::getApplicationDate, endDate)
                .list();
    }

    @Override
    public List<CertificateApplication> getCertificateApplicationsByStatus(String status) {
        return lambdaQuery().eq(CertificateApplication::getStatus, status).list();
    }

    @Override
    public List<CertificateApplication> getCertificateApplicationsByTenantId(Long tenantId) {
        return lambdaQuery().eq(CertificateApplication::getTenantId, tenantId).list();
    }

    @Override
    public CertificateApplication approveCertificateApplication(Long id) {
        CertificateApplication application = getById(id);
        if (application != null) {
            application.setStatus("approved");
            updateById(application);
        }
        return application;
    }

    @Override
    public CertificateApplication rejectCertificateApplication(Long id, String reason) {
        CertificateApplication application = getById(id);
        if (application != null) {
            application.setStatus("rejected");
            application.setRejectionReason(reason);
            updateById(application);
        }
        return application;
    }
}