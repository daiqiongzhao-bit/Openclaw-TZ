package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.SocialSecurityBill;

import java.time.LocalDate;
import java.util.List;

public interface SocialSecurityBillService extends IService<SocialSecurityBill> {
    boolean createBill(SocialSecurityBill bill);
    List<SocialSecurityBill> getBillsByTenantId(Long tenantId);
    List<SocialSecurityBill> getBillsByMonth(LocalDate billMonth);
    List<SocialSecurityBill> getBillsByDateRange(LocalDate startDate, LocalDate endDate);
    SocialSecurityBill getBillById(Long id);
    boolean verifyBill(Long id, Integer status);
    boolean rejectBill(Long id);
}