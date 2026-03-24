package com.hr.controller;

import com.hr.entity.CertificateApplication;
import com.hr.service.CertificateApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/employee-relation/certificate")
public class CertificateApplicationController {
    @Autowired
    private CertificateApplicationService certificateApplicationService;

    @PostMapping
    public CertificateApplication submitCertificateApplication(@RequestBody CertificateApplication application) {
        return certificateApplicationService.submitCertificateApplication(application);
    }

    @PutMapping
    public CertificateApplication updateCertificateApplication(@RequestBody CertificateApplication application) {
        return certificateApplicationService.updateCertificateApplication(application);
    }

    @DeleteMapping("/{id}")
    public void deleteCertificateApplication(@PathVariable Long id) {
        certificateApplicationService.deleteCertificateApplication(id);
    }

    @GetMapping("/{id}")
    public CertificateApplication getCertificateApplicationById(@PathVariable Long id) {
        return certificateApplicationService.getCertificateApplicationById(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<CertificateApplication> getCertificateApplicationsByEmployeeId(@PathVariable Long employeeId) {
        return certificateApplicationService.getCertificateApplicationsByEmployeeId(employeeId);
    }

    @GetMapping("/type/{type}")
    public List<CertificateApplication> getCertificateApplicationsByType(@PathVariable String type) {
        return certificateApplicationService.getCertificateApplicationsByType(type);
    }

    @GetMapping("/date-range")
    public List<CertificateApplication> getCertificateApplicationsByDateRange(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return certificateApplicationService.getCertificateApplicationsByDateRange(startDate, endDate);
    }

    @GetMapping("/status/{status}")
    public List<CertificateApplication> getCertificateApplicationsByStatus(@PathVariable String status) {
        return certificateApplicationService.getCertificateApplicationsByStatus(status);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<CertificateApplication> getCertificateApplicationsByTenantId(@PathVariable Long tenantId) {
        return certificateApplicationService.getCertificateApplicationsByTenantId(tenantId);
    }

    @PutMapping("/approve/{id}")
    public CertificateApplication approveCertificateApplication(@PathVariable Long id) {
        return certificateApplicationService.approveCertificateApplication(id);
    }

    @PutMapping("/reject/{id}")
    public CertificateApplication rejectCertificateApplication(@PathVariable Long id, @RequestParam String reason) {
        return certificateApplicationService.rejectCertificateApplication(id, reason);
    }
}