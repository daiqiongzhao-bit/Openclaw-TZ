package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.CertificateApplication;

import java.time.LocalDate;
import java.util.List;

public interface CertificateApplicationService extends IService<CertificateApplication> {
    CertificateApplication submitCertificateApplication(CertificateApplication application);
    CertificateApplication updateCertificateApplication(CertificateApplication application);
    void deleteCertificateApplication(Long id);
    CertificateApplication getCertificateApplicationById(Long id);
    List<CertificateApplication> getCertificateApplicationsByEmployeeId(Long employeeId);
    List<CertificateApplication> getCertificateApplicationsByType(String type);
    List<CertificateApplication> getCertificateApplicationsByDateRange(LocalDate startDate, LocalDate endDate);
    List<CertificateApplication> getCertificateApplicationsByStatus(String status);
    List<CertificateApplication> getCertificateApplicationsByTenantId(Long tenantId);
    CertificateApplication approveCertificateApplication(Long id);
    CertificateApplication rejectCertificateApplication(Long id, String reason);
}