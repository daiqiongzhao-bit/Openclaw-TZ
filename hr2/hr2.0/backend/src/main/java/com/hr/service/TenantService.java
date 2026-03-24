package com.hr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hr.entity.Tenant;

import java.util.List;

public interface TenantService extends IService<Tenant> {
    List<Tenant> getAllTenants();
    boolean saveTenant(Tenant tenant);
    boolean updateTenant(Tenant tenant);
    boolean deleteTenant(Long id);
    boolean enableTenant(Long id);
    boolean disableTenant(Long id);
    Tenant getTenantByCode(String tenantCode);
}
